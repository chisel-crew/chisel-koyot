package koyot

import io.grpc.ManagedChannelBuilder
import firsim.firrtl._
import firsim.firrtl.ZioFirrtl._
import zio.console.{ putStrLn, Console }
import scalapb.zio_grpc.ZManagedChannel
import zio.Layer
import zio.stream.{ Stream }

object KoyotClient {

  private val clientLayer: Layer[Throwable, SimClient] =
    SimClient.live(
      ZManagedChannel(
        ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext()
      )
    )

  def getVerilog(firdata: List[String]) =
    (for {
      _    <- putStrLn(">>> Started converting FIRRTL to Verilog")
      resp <- SimClient.procStream(Stream.fromIterable(List(SimRequest(firdata)))).runCollect
      _    <- putStrLn(">>> Response")
      _    <- putStrLn(resp.map(_.resp.toString).toString)
    } yield ())
      .onError(c => putStrLn(c.prettyPrint))
      .provideLayer(Console.live ++ clientLayer)
}
