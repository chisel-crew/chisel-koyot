package demo

import hw._

object App0 extends zio.App {
  def run(args: List[String]) = app1.exitCode

  val bufCircuit = Seq(
    chisel3.stage.ChiselGeneratorAnnotation(() => new Regbuffer())
  )

  val cntCircuit = Seq(
    chisel3.stage.ChiselGeneratorAnnotation(() => new Counter(2))
  )

  // Output Circtuits
  val app0 = KoyotApi.getVerilog(bufCircuit)
  val app1 = KoyotApi.getVerilog(cntCircuit)
  val app2 = KoyotApi.getFirrtl(cntCircuit)

}
