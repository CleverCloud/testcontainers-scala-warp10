package com.clevercloud.testcontainers.scala

/**
 * Represents the cryptographic keys used by Warp10 for token generation.
 *
 * These keys are extracted from the Warp10 container's configuration file
 * at `/opt/warp10/etc/conf.d/99-init.conf` after container startup.
 *
 * Key format and sizes:
 *   - AES Token Key: 64 hex characters (32 bytes / 256 bits) - Used for token encryption
 *   - SIP Hash App: 32 hex characters (16 bytes / 128 bits) - Used for application hashing
 *   - SIP Hash Token: 32 hex characters (16 bytes / 128 bits) - Used for token hashing
 *
 * @param aesTokenKey  The AES key for token encryption (64 hex chars = 32 bytes)
 * @param sipHashApp   The SipHash key for application hashing (32 hex chars = 16 bytes)
 * @param sipHashToken The SipHash key for token hashing (32 hex chars = 16 bytes)
 */
case class Warp10CryptoKeys(
    aesTokenKey: String,
    sipHashApp: String,
    sipHashToken: String
) {

  /**
   * Validates that all keys have the expected lengths.
   *
   * @return true if all keys are valid, false otherwise
   */
  def isValid: Boolean =
    aesTokenKey != null && aesTokenKey.length == 64 &&
      sipHashApp != null && sipHashApp.length == 32 &&
      sipHashToken != null && sipHashToken.length == 32

  /**
   * Returns a string representation with redacted key values for security.
   */
  override def toString: String =
    s"Warp10CryptoKeys(aesTokenKey=[REDACTED:${Option(aesTokenKey).map(_.length).getOrElse(0)} chars], " +
      s"sipHashApp=[REDACTED:${Option(sipHashApp).map(_.length).getOrElse(0)} chars], " +
      s"sipHashToken=[REDACTED:${Option(sipHashToken).map(_.length).getOrElse(0)} chars])"
}

object Warp10CryptoKeys {

  /**
   * Creates a Warp10CryptoKeys instance from the Java container's crypto keys.
   *
   * @param javaKeys The Java Warp10CryptoKeys from the underlying container
   * @return A Scala Warp10CryptoKeys instance, or None if javaKeys is null
   */
  def fromJava(
      javaKeys: com.clevercloud.testcontainers.warp10.Warp10CryptoKeys
  ): Option[Warp10CryptoKeys] =
    Option(javaKeys).map { keys =>
      Warp10CryptoKeys(
        aesTokenKey = keys.getAesTokenKey,
        sipHashApp = keys.getSipHashApp,
        sipHashToken = keys.getSipHashToken
      )
    }
}
