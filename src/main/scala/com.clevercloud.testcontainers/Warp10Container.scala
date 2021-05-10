package com.clevercloud.testcontainers

import com.clevercloud.testcontainers.warp10.{Warp10Container => JavaWarp10Container}
import org.testcontainers.utility.DockerImageName
import com.dimafeng.testcontainers._

case class Warp10Container(
  tag: String,
) extends SingleContainer[JavaWarp10Container] {

  override val container: JavaWarp10Container = new JavaWarp10Container(tag)

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
      new Warp10Container(tag)
    }

  }

}
