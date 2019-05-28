package gdg.aracaju.domain.service

import gdg.aracaju.domain.model.Detail

interface DetailService {

    suspend fun fetchDetail(id: String): Detail
}