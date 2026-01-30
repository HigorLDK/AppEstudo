package com.example.appestudo.ui.screens.infoPet.prontuario


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appestudo.R
import com.example.appestudo.data.model.PetInfo
import com.example.appestudo.ui.theme.AppEstudoTheme
import com.example.appestudo.ui.theme.Yellow10

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProntuariopetInfoScreen(
    petInfoId: String,
    navController: NavController,
    viewModel: ProntuarioViewModel = viewModel()
) {

    Log.d("PET_DEBUG", "Tela recebeu petInfoId = $petInfoId")

    val petInfo by viewModel.getPet(petInfoId).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Prontuário") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Yellow10
                )
            )
        }
    ) { padding ->

        if (petInfo == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFF7F7F7)),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item { HeaderpetInfo() }

                item { Spacer(modifier = Modifier.height(20.dp)) }

                item { IdentificacaoSectionReadOnly(petInfo!!) }

                item {
                    MultilineReadOnlySection(
                        "Queixa principal / Histórico recente",
                        petInfo!!.queixa
                    )
                }

                item { HistoricoClinicoReadOnly(petInfo!!) }

                item { ParametrosClinicosReadOnly(petInfo!!) }

                item { AnamneseReadOnly(petInfo!!) }

                item { AlimentacaoReadOnly(petInfo!!) }

                item {
                    MultilineReadOnlySection(
                        "Conduta clínica",
                        petInfo!!.conduta
                    )
                }

                item {
                    MultilineReadOnlySection(
                        "Tratamentos e exames solicitados",
                        petInfo!!.tratamentos
                    )
                }

                item {
                    MultilineReadOnlySection(
                        "Retorno",
                        petInfo!!.retorno
                    )
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}

/* ---------------- HEADER ---------------- */

@Composable
fun HeaderpetInfo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFFFFEB3B)),
                    contentAlignment = Alignment.Center
        ) {
        }
        Box(
            modifier = Modifier
                .size(160.dp)
                .offset(y = 10.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.teste),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}



/* ---------------- IDENTIFICAÇÃO ---------------- */

@Composable
fun IdentificacaoSectionReadOnly(petInfo: PetInfo) {
    CardSection("Identificação do paciente") {
        InfoRow("Paciente", petInfo.nome)
        InfoRow("Espécie", petInfo.especie)
        InfoRow("Raça", petInfo.raca)
        InfoRow("Peso", "${petInfo.peso} kg")
        InfoRow("Idade", "${petInfo.idade} anos")
        Divider()
        InfoRow("Tutor", petInfo.tutor)
        InfoRow("CPF", petInfo.cpf)
        InfoRow("Telefone", petInfo.telefone)
        InfoRow("Endereço", petInfo.endereco)
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(value, fontWeight = FontWeight.Medium)
    }
}

/* ---------------- SEÇÕES MULTILINHA ---------------- */

@Composable
fun MultilineReadOnlySection(title: String, text: String) {
    CardSection(title) {
        Text(
            text = text,
            color = Color.DarkGray,
            lineHeight = 20.sp
        )
    }
}

/* ---------------- HISTÓRICO CLÍNICO ---------------- */

@Composable
fun HistoricoClinicoReadOnly(petInfo: PetInfo) {
    CardSection("Histórico clínico") {
        BooleanReadOnly("Vacinação", petInfo.vacinacao)
        BooleanReadOnly("Controle endoparasitas", petInfo.endoparasitas)
        BooleanReadOnly("Controle ectoparasitas", petInfo.ectoparasitas)
        BooleanReadOnly("Uso contínuo de medicação", petInfo.medicacaoContinua)
        BooleanReadOnly("Uso de suplementação", petInfo.suplementacao)
        BooleanReadOnly("Realizou cirurgias", petInfo.cirurgias)
        BooleanReadOnly("Possui exame recente", petInfo.exameRecente)
        BooleanReadOnly("Felino testado FIV/FELV", petInfo.felinoTestado)
        BooleanReadOnly("Alergia a medicamentos", petInfo.alergiaMedicamento)
    }
}

@Composable
fun BooleanReadOnly(label: String, value: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(if (value) "Sim" else "Não", fontWeight = FontWeight.SemiBold)
    }
}

/* ---------------- PARÂMETROS CLÍNICOS ---------------- */

@Composable
fun ParametrosClinicosReadOnly(petInfo: PetInfo) {
    CardSection("Parâmetros clínicos") {
        StatusReadOnly("Hidratação", petInfo.hidratacao)
        StatusReadOnly("Glicemia", petInfo.glicemia)
        StatusReadOnly("Mucosa", petInfo.mucosa)
        StatusReadOnly("Pelagem", petInfo.pelagem)
        StatusReadOnly("Frequência cardíaca", petInfo.freqCardiaca)
        StatusReadOnly("Frequência respiratória", petInfo.freqRespiratoria)
        StatusReadOnly("Cavidade oral", petInfo.cavidadeOral)
        StatusReadOnly("Condutores auditivos", petInfo.condutoresAuditivos)
        StatusReadOnly("Temperatura", petInfo.temperatura)
        StatusReadOnly("Pressão arterial sistêmica", petInfo.pressao)
        StatusReadOnly("Linfonodos", petInfo.linfonodos)
        StatusReadOnly("Deambulação", petInfo.deambulacao)
        StatusReadOnly("Propriocepção", petInfo.propriocepcao)
        StatusReadOnly("Palpação abdominal", petInfo.palpacaoAbdominal)
        StatusReadOnly("Cavidade nasal", petInfo.cavidadeNasal)
        StatusReadOnly("Oftalmológicos", petInfo.oftalmologicos)
    }
}


@Composable
fun StatusReadOnly(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(value, fontWeight = FontWeight.Medium, color = Color.Gray)
    }
}

/* ---------------- ANAMNESE ---------------- */

@Composable
fun AnamneseReadOnly(petInfo: PetInfo) {
    CardSection("Anamnese") {
        BooleanReadOnly("Doenças pregressas", petInfo.doencasPregressas)
        BooleanReadOnly("Doenças presentes", petInfo.doencasPresentes)

        ListReadOnly("Sistema digestório", petInfo.digestorio)
        ListReadOnly("Sistema urogenital", petInfo.urogenital)
        ListReadOnly("Sistema cardiorrespiratório", petInfo.cardiorrespiratorio)
        ListReadOnly("Sistema neurológico", petInfo.neurologico)
        ListReadOnly("Sistema locomotor", petInfo.locomotor)
        ListReadOnly("Pele", petInfo.pele)
        ListReadOnly("Olhos", petInfo.olhos)
        ListReadOnly("Ouvido", petInfo.ouvido)
        ListReadOnly("Ambiente", petInfo.ambiente)
        ListReadOnly("Contactantes", petInfo.contactantes)
        ListReadOnly("Produtos tóxicos", petInfo.produtosToxicos)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListReadOnly(title: String, items: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, fontWeight = FontWeight.SemiBold)
        if (items.isEmpty()) {
            Text("Nenhuma alteração", color = Color.Gray, fontSize = 13.sp)
        } else {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items.forEach {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Color(0xFFE0F2F1)
                    ) {
                        Text(it, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

/* ---------------- ALIMENTAÇÃO ---------------- */

@Composable
fun AlimentacaoReadOnly(petInfo: PetInfo) {
    CardSection("Alimentação") {
        ListReadOnly("Ração e petiscos", petInfo.racaoPetiscos)
        ListReadOnly("Alimentação natural", petInfo.alimentacaoNatural)
    }
}

/* ---------------- COMPONENTES BASE ---------------- */

@Composable
fun CardSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF2F7D6C))
            content()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProntuariopetInfoPreview() {
    AppEstudoTheme {

        val fakePet = PetInfo(
            id = "1",
            nome = "Thor",
            especie = "Canino",
            raca = "Golden Retriever",
            peso = 28.0,
            idade = 4,
            tutor = "Marcos Silva",
            cpf = "123.456.789-00",
            telefone = "(11) 99999-0000",
            endereco = "Rua das Flores, 123",

            vacinacao = true,
            endoparasitas = true,
            ectoparasitas = false,
            medicacaoContinua = false,
            suplementacao = true,
            cirurgias = false,
            exameRecente = true,
            felinoTestado = false,
            alergiaMedicamento = false,

            hidratacao = "Normal",
            glicemia = "Normal",
            mucosa = "Normal",
            pelagem = "Normal",
            freqCardiaca = "Normal",
            freqRespiratoria = "Normal",
            cavidadeOral = "Normal",
            condutoresAuditivos = "Normal",
            temperatura = "38.5°C",
            pressao = "120/80",
            linfonodos = "Normais",
            deambulacao = "Normal",
            propriocepcao = "Normal",
            palpacaoAbdominal = "Sem dor",
            cavidadeNasal = "Normal",
            oftalmologicos = "Normais",

            doencasPregressas = false,
            doencasPresentes = true,
            digestorio = listOf("Vômito", "Diarreia"),
            urogenital = listOf("Urina normal"),
            cardiorrespiratorio = listOf("Tosse"),
            neurologico = emptyList(),
            locomotor = emptyList(),
            pele = listOf("Prurido"),
            olhos = emptyList(),
            ouvido = emptyList(),
            ambiente = listOf("Urbano"),
            contactantes = emptyList(),
            produtosToxicos = emptyList(),

            racaoPetiscos = listOf("Ração seca comercial", "Petiscos"),
            alimentacaoNatural = emptyList(),

            queixa = "Paciente apresentou episódios de vômito e apatia há 2 dias.",
            conduta = "Prescrito antiemético e dieta leve por 3 dias.",
            tratamentos = "Solicitado hemograma completo e ultrassonografia abdominal.",
            retorno = "Reavaliar em 7 dias."
        )

        PreviewProntuarioContent(fakePet)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PreviewProntuarioContent(petInfo: PetInfo) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Prontuário") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Yellow10
                )
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF7F7F7)),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item { HeaderpetInfo() }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            item { IdentificacaoSectionReadOnly(petInfo) }

            item {
                MultilineReadOnlySection(
                    "Queixa principal / Histórico recente",
                    petInfo.queixa
                )
            }

            item { HistoricoClinicoReadOnly(petInfo) }

            item { ParametrosClinicosReadOnly(petInfo) }

            item { AnamneseReadOnly(petInfo) }

            item { AlimentacaoReadOnly(petInfo) }

            item {
                MultilineReadOnlySection(
                    "Conduta clínica",
                    petInfo.conduta
                )
            }

            item {
                MultilineReadOnlySection(
                    "Tratamentos e exames solicitados",
                    petInfo.tratamentos
                )
            }

            item {
                MultilineReadOnlySection(
                    "Retorno",
                    petInfo.retorno
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
