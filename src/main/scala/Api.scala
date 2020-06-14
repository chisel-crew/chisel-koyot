package demo

import koyot.client._
import zio.console.{ putStrLn }
import chisel3.stage.ChiselGeneratorAnnotation

object KoyotApi {
  private val firhome = "fir/"

  val ping = for {
    resp <- Koyot.ping()
    _    <- putStrLn(resp.toString)
  } yield resp

  def getVerilog(circuit: Seq[ChiselGeneratorAnnotation]) = Koyot.emit(firhome, circuit, Verilog)
  def getFirrtl(circuit: Seq[ChiselGeneratorAnnotation])  = Koyot.emit(firhome, circuit, High)
}
