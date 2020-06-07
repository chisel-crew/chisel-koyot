package demo

import koyot._
import koyot.emiter._
import hw.Counter
import zio.ZIO

object App0 extends zio.App {
  def run(args: List[String]) = app0.exitCode

  val firhome = "src/main/scala/fir/"

  val cntCircuit = Seq(
    chisel3.stage.ChiselGeneratorAnnotation(() => new Counter(2))
  )

  val app0 = {
    Emiter.emit(firhome, cntCircuit, High)
    ZIO.unit
    // KoyotClient.getVerilog(firdata)
  }
}
