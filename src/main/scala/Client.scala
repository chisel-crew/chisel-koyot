package firsim

import io.grpc.ManagedChannelBuilder
import firsim.firrtl._
import firsim.firrtl.ZioFirrtl._
import zio.console.{ putStrLn, Console }
import scalapb.zio_grpc.ZManagedChannel
import zio.Layer
import zio.stream.{ Stream }

object KoyotClient {

  def clientLayer: Layer[Throwable, SimClient] =
    SimClient.live(
      ZManagedChannel(
        ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext()
      )
    )

  val data = List("Hello Boris", "How are you")

  def myAppLogic =
    (for {
      r0 <- SimClient.proc(SimRequest(data))
      _  <- putStrLn(">>> Effect response")
      _  <- putStrLn(r0.resp.toString)
      r1 <- SimClient.procStream(Stream.fromIterable(List(SimRequest(data)))).runCollect
      _  <- putStrLn(">>> Stream response")
      _  <- putStrLn(r1.map(_.resp.toString).toString)
    } yield ())
      .onError(c => putStrLn(c.prettyPrint))
      .provideLayer(Console.live ++ clientLayer)
}
