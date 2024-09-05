package com.monta.library.ocpp.v16.extension.plugandcharge.util

import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

object CertificateUtil {
    @JvmStatic
    private val logger = LoggerFactory.getLogger(CertificateUtil::class.java)

    fun stringToX509Cert(
        certificate: String?
    ): X509Certificate? {
        certificate ?: return null
        return try {
            val targetStream: InputStream = ByteArrayInputStream(certificate.toByteArray())
            targetStream.use { stream ->
                CertificateFactory
                    .getInstance("X509")
                    .generateCertificate(stream) as X509Certificate
            }
        } catch (exception: CertificateException) {
            logger.error("convertStringToX509Cert", exception)
            null
        }
    }
}
