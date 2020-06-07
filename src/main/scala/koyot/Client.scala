package koyot

import io.grpc.ManagedChannelBuilder
import firsim.firrtl._
import firsim.firrtl.ZioFirrtl._
import zio.console.{ putStrLn, Console }
import scalapb.zio_grpc.ZManagedChannel
import zio.{ Layer, ZIO }
import zio.stream.{ Stream }

import scala.io.Source.fromFile

object Koyot {

  private val clientLayer: Layer[Throwable, SimClient] =
    SimClient.live(
      ZManagedChannel(
        ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext()
      )
    )

  /**
   * Loads FIRRTL from a generated file
   */
  def load(path: String): String = fromFile(path).getLines.fold("")(_ + _ + '\n')

  /**
   * Validates connection to the server
   */
  def ping() = ZIO.unit

  /**
   * Translates FIRRTL to Verilog
   */
  def getVerilog(firdata: String) =
    (for {
      _    <- putStrLn(">>> Started converting FIRRTL to Verilog")
      resp <- SimClient.procStream(Stream.fromIterable(List(SimRequest(Seq(firdata))))).runCollect
      _    <- putStrLn(">>> Response")
      _    <- putStrLn(resp.map(_.resp.toString).toString)
    } yield ())
      .onError(c => putStrLn(c.prettyPrint))
      .provideLayer(Console.live ++ clientLayer)
}
