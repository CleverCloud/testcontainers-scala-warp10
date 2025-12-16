package com.clevercloud.testcontainers.scala

import com.clevercloud.testcontainers.warp10.{Warp10Container => JavaWarp10Container}
import com.dimafeng.testcontainers._

import java.io.File

/** Create a Warp10 container
 *
 * @param tag          The version tag for the docker image
 * @param macrosFolder Optional File that points to a folder of macros to install server-side.
 * @param configFolder Optional File that points to a folder of config file templates.
 *                     The files inside the folder **must** be named like NN-something.conf.template,
 *                     as it will be put inside the template folder.
 */
case class Warp10Container(
  tag: String,
  macrosFolder: Option[File] = None,
  configFolder: Option[File] = None,
) extends SingleContainer[JavaWarp10Container] {

  override val container: JavaWarp10Container = (macrosFolder, configFolder) match {
    case (None, None)         => new JavaWarp10Container(tag)
    case (Some(f), None)      => new JavaWarp10Container(tag, f)
    case (None, Some(f2))     => new JavaWarp10Container(tag, null, f2)
    case (Some(f1), Some(f2)) => new JavaWarp10Container(tag, f1, f2)
  }

  def httpHost: String = container.getHTTPHost

  def httpPort: Int = container.getHTTPPort()

  def httpHostAddress: String = container.getHTTPHostAddress

  def protocol: String = container.getProtocol

  def readToken: String = container.getReadToken

  def writeToken: String = container.getWriteToken

  /**
   * Gets all crypto keys as a Scala case class.
   *
   * @return Some(Warp10CryptoKeys) containing AES and SipHash keys, or None if not yet extracted
   */
  def cryptoKeys: Option[Warp10CryptoKeys] =
    Warp10CryptoKeys.fromJava(container.getCryptoKeys)

  /**
   * Gets the AES key used for token encryption.
   *
   * @return The AES token key as a hex string (64 characters = 32 bytes), or null if not available
   */
  def aesTokenKey: String = container.getAesTokenKey

  /**
   * Gets the SipHash key used for application hashing.
   *
   * @return The SipHash app key as a hex string (32 characters = 16 bytes), or null if not available
   */
  def sipHashApp: String = container.getSipHashApp

  /**
   * Gets the SipHash key used for token hashing.
   *
   * @return The SipHash token key as a hex string (32 characters = 16 bytes), or null if not available
   */
  def sipHashToken: String = container.getSipHashToken
}

object Warp10Container {
  val defaultTag = "3.4.1-ubuntu-ci"

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
