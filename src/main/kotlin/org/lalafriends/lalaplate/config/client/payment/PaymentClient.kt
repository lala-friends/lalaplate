package org.lalafriends.lalaplate.config.client.payment

interface PaymentClient {
    fun pay(request: PaymentClientResources.Request.Payment): PaymentClientResources.Response.PaymentResult
}
