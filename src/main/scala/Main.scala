package demo

import koyot.client._
import hw.Counter
import zio.{ ZIO }
import zio.console.{ putStrLn }

object App0 extends zio.App {
  def run(args: List[String]) = app1.exitCode

  val firhome = "fir/"
  val circuit = firhome + "Counter.fir"

  val cntCircuit = Seq(
    chisel3.stage.ChiselGeneratorAnnotation(() => new Counter(2))
  )

  val app0 = {
    Koyot.emit(firhome, cntCircuit, High)
    ZIO.unit
  }

  val app1 = for {
    resp <- Koyot.ping()
    _    <- putStrLn(resp.toString)
  } yield resp

}
