package org.lalafriends.lalaplate.common

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.lalafriends.lalaplate.common.config.AppEnv
import org.springframework.stereotype.Component

@Converter
@Component
class AttributeEncryptor(
    appEnv: AppEnv,
) : AttributeConverter<String, String> {
    private val crypto =
        Crypto(
            appEnv.cryptoKey.attributeEncryptKey!!,
            appEnv.cryptoKey.iv!!,
        )

    override fun convertToDatabaseColumn(attribute: String?): String? =
        if (attribute.isNullOrEmpty()) {
            attribute
        } else {
            crypto.enc(attribute)
        }

    override fun convertToEntityAttribute(dbData: String?): String? =
        if (dbData.isNullOrEmpty()) {
            dbData
        } else {
            crypto.dec(dbData, String::class.java)
        }
}
