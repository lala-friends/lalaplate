package org.lalafriends.lalaplate.common.config.client.payment

interface PaymentClient {
    fun pay(request: PaymentClientResources.Request.Payment): PaymentClientResources.Response.PaymentResult
}
