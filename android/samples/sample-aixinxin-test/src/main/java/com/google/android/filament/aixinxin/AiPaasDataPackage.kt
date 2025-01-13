@file:Suppress("PropertyName")

package com.google.android.filament.aixinxin

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class SpeechRsp(val audio: String, val sampling: Int)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class CoefFrame(val coef_frame: HashMap<String, Float>)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class AlgInfo(val anim_coef_list: List<CoefFrame>)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class AnimRep(val alg_info: AlgInfo)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class SpeechRepInfo(val speech_rsp: SpeechRsp, val anim_rep: AnimRep)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class AiPaasDataPackage(val speech_rep_info: SpeechRepInfo)
