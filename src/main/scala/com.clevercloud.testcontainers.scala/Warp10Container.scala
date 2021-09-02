package com.clevercloud.testcontainers.scala

import com.clevercloud.testcontainers.warp10.{Warp10Container => JavaWarp10Container}
import com.dimafeng.testcontainers._

import java.io.File

case class Warp10Container(
  tag: String,
  macrosFolder: Option[File] = None,
) extends SingleContainer[JavaWarp10Container] {

  override val container: JavaWarp10Container = macrosFolder match {
    case None    => new JavaWarp10Container(tag)
    case Some(f) => new JavaWarp10Container(tag, f)
  }

  def httpHost: String = container.getHTTPHost

  def httpPort: Int = container.getHTTPPort()

  def httpHostAddress: String = container.getHTTPHostAddress

  def protocol: String = container.getProtocol

  def readToken: String = container.getReadToken

  def writeToken: String = container.getWriteToken
}

object Warp10Container {
  val defaultTag = "2.7.5"

  case class Def(
    tag: String,
  ) extends ContainerDef {
    override type Container = Warp10Container

    override def createContainer(): Warp10Container = {
      Warp10Container(tag)
    }

    def createWithMacros(macrosFolder: File): Warp10Container = {
      Warp10Container(tag, Some(macrosFolder))
    }

  }

}
