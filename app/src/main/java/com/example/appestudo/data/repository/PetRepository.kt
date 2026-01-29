package com.example.appestudo.data.repository

import android.util.Log
import com.example.appestudo.data.model.PetInfo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PetRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getPets(): List<PetInfo> {
        return firestore.collection("pets")
            .get()
            .await()
            .documents
            .map { doc ->
                Log.d("PET_DEBUG", "Firebase retornou: ${doc.data}")
                PetInfo(
                    id = doc.id,
                    nome = doc.getString("nome") ?: "Lara",
                    raca = doc.getString("raca") ?: "",
                    idade = doc.getLong("idade")?.toInt() ?: 0,
                    castrado = doc.getBoolean("castrado") ?: false,
                    fotoUrl = doc.getString("fotoUrl") ?: "",

                    // Identificação
                    especie = doc.getString("especie") ?: "",
                    peso = doc.getDouble("peso") ?: 0.0,
                    tutor = doc.getString("tutor") ?: "",
                    cpf = doc.getString("cpf") ?: "",
                    telefone = doc.getString("telefone") ?: "",
                    endereco = doc.getString("endereco") ?: "",

                    // Histórico clínico
                    vacinacao = doc.getBoolean("vacinacao") ?: false,
                    endoparasitas = doc.getBoolean("endoparasitas") ?: false,
                    ectoparasitas = doc.getBoolean("ectoparasitas") ?: false,
                    medicacaoContinua = doc.getBoolean("medicacaoContinua") ?: false,
                    suplementacao = doc.getBoolean("suplementacao") ?: false,
                    cirurgias = doc.getBoolean("cirurgias") ?: false,
                    exameRecente = doc.getBoolean("exameRecente") ?: false,
                    felinoTestado = doc.getBoolean("felinoTestado") ?: false,
                    alergiaMedicamento = doc.getBoolean("alergiaMedicamento") ?: false,

                    // Parâmetros clínicos
                    hidratacao = doc.getString("hidratacao") ?: "",
                    glicemia = doc.getString("glicemia") ?: "",
                    mucosa = doc.getString("mucosa") ?: "",
                    pelagem = doc.getString("pelagem") ?: "",
                    freqCardiaca = doc.getString("freqCardiaca") ?: "",
                    freqRespiratoria = doc.getString("freqRespiratoria") ?: "",
                    cavidadeOral = doc.getString("cavidadeOral") ?: "",
                    condutoresAuditivos = doc.getString("condutoresAuditivos") ?: "",
                    temperatura = doc.getString("temperatura") ?: "",
                    pressao = doc.getString("pressao") ?: "",
                    linfonodos = doc.getString("linfonodos") ?: "",
                    deambulacao = doc.getString("deambulacao") ?: "",
                    propriocepcao = doc.getString("propriocepcao") ?: "",
                    palpacaoAbdominal = doc.getString("palpacaoAbdominal") ?: "",
                    cavidadeNasal = doc.getString("cavidadeNasal") ?: "",
                    oftalmologicos = doc.getString("oftalmologicos") ?: "",

                    // Anamnese
                    doencasPregressas = doc.getBoolean("doencasPregressas") ?: false,
                    doencasPresentes = doc.getBoolean("doencasPresentes") ?: false,
                    digestorio = doc.get("digestorio") as? List<String> ?: emptyList(),
                    urogenital = doc.get("urogenital") as? List<String> ?: emptyList(),
                    cardiorrespiratorio = doc.get("cardiorrespiratorio") as? List<String> ?: emptyList(),
                    neurologico = doc.get("neurologico") as? List<String> ?: emptyList(),
                    locomotor = doc.get("locomotor") as? List<String> ?: emptyList(),
                    pele = doc.get("pele") as? List<String> ?: emptyList(),
                    olhos = doc.get("olhos") as? List<String> ?: emptyList(),
                    ouvido = doc.get("ouvido") as? List<String> ?: emptyList(),
                    ambiente = doc.get("ambiente") as? List<String> ?: emptyList(),
                    contactantes = doc.get("contactantes") as? List<String> ?: emptyList(),
                    produtosToxicos = doc.get("produtosToxicos") as? List<String> ?: emptyList(),

                    // Alimentação
                    racaoPetiscos = doc.get("racaoPetiscos") as? List<String> ?: emptyList(),
                    alimentacaoNatural = doc.get("alimentacaoNatural") as? List<String> ?: emptyList(),

                    // Clínico final
                    queixa = doc.getString("queixa") ?: "",
                    conduta = doc.getString("conduta") ?: "",
                    tratamentos = doc.getString("tratamentos") ?: "",
                    retorno = doc.getString("retorno") ?: ""
                )
            }
    }
}
