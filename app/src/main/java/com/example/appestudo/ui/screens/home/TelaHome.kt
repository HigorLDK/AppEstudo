package com.example.appestudo.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHome(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        //Botões superiores
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            TopActionButton("Marcação", Icons.AutoMirrored.Filled.List) {
                navController.navigate("marcacao")
            }
            TopActionButton("Produtos", Icons.Default.DateRange) {}
            TopActionButton("Cuidados", Icons.Default.FavoriteBorder) {}
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Meus Pets",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        val pets by viewModel.pets.collectAsState()

        //Lista de Pets
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(pets) { pet ->
                PetCard(pet, onClick = {navController.navigate("prontuario/${pet.id}")})
                Spacer(modifier = Modifier.height(12.dp))
            }

        }
    }
}


@Composable
fun TopActionButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(90.dp)
            .background(Color(0xFFFFEB3B), RoundedCornerShape(24.dp))
            .clickable { onClick()}
            .padding(12.dp)
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun PetCard(pet: PetInfo, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // PARTE 1 — Foto + textos
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(85.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4CAF50), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.teste),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(pet.nome, fontWeight = FontWeight.Bold)
                        Text(pet.raca, fontSize = 12.sp, color = Color.Gray)
                    }
                }

                // PARTE 2 — Badge lateral
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(80.dp)
                        .background(
                            if (pet.castrado) Color(0xFF69F0AE) else Color(0xFFFF6E6E),
                            RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (pet.castrado) "Castrado" else "Não castrado",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPrimeiraTela() {
    AppEstudoTheme {
        PrimeiraTelaPreviewContent()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewPrimeiraTelaDark() {
    AppEstudoTheme(darkTheme = true) {
        PrimeiraTelaPreviewContent()
    }
}

@Composable
private fun PrimeiraTelaPreviewContent() {
    val fakePets = listOf(
        PetInfo(
            id = "01",
            nome = "Piolho",
            raca = "Golden Retriever",
            especie = "Felino",
            peso = 28.0,
            idade = 4,
            tutor = "Marcos",
            cpf = "",
            telefone = "",
            endereco = "",
            vacinacao = true,
            endoparasitas = true,
            ectoparasitas = false,
            medicacaoContinua = false,
            suplementacao = false,
            cirurgias = false,
            exameRecente = false,
            felinoTestado = false,
            alergiaMedicamento = false,
            hidratacao = "",
            glicemia = "",
            mucosa = "",
            pelagem = "",
            freqCardiaca = "",
            freqRespiratoria = "",
            cavidadeOral = "",
            condutoresAuditivos = "",
            temperatura = "",
            pressao = "",
            linfonodos = "",
            deambulacao = "",
            propriocepcao = "",
            palpacaoAbdominal = "",
            cavidadeNasal = "",
            oftalmologicos = "",
            doencasPregressas = false,
            doencasPresentes = false,
            digestorio = emptyList(),
            urogenital = emptyList(),
            cardiorrespiratorio = emptyList(),
            neurologico = emptyList(),
            locomotor = emptyList(),
            pele = emptyList(),
            olhos = emptyList(),
            ouvido = emptyList(),
            ambiente = emptyList(),
            contactantes = emptyList(),
            produtosToxicos = emptyList(),
            racaoPetiscos = emptyList(),
            alimentacaoNatural = emptyList(),
            queixa = "",
            conduta = "",
            tratamentos = "",
            retorno = ""
        ),
        /* segundo pet */
        PetInfo(
            id = "2",
            nome = "Luna",
            raca = "Siamês",
            especie = "Felino",
            castrado = true,
            peso = 4.2,
            idade = 2,
            tutor = "",
            cpf = "",
            telefone = "",
            endereco = "",
            vacinacao = true,
            endoparasitas = true,
            ectoparasitas = true,
            medicacaoContinua = false,
            suplementacao = false,
            cirurgias = false,
            exameRecente = false,
            felinoTestado = true,
            alergiaMedicamento = false,
            hidratacao = "",
            glicemia = "",
            mucosa = "",
            pelagem = "",
            freqCardiaca = "",
            freqRespiratoria = "",
            cavidadeOral = "",
            condutoresAuditivos = "",
            temperatura = "",
            pressao = "",
            linfonodos = "",
            deambulacao = "",
            propriocepcao = "",
            palpacaoAbdominal = "",
            cavidadeNasal = "",
            oftalmologicos = "",
            doencasPregressas = false,
            doencasPresentes = false,
            digestorio = emptyList(),
            urogenital = emptyList(),
            cardiorrespiratorio = emptyList(),
            neurologico = emptyList(),
            locomotor = emptyList(),
            pele = emptyList(),
            olhos = emptyList(),
            ouvido = emptyList(),
            ambiente = emptyList(),
            contactantes = emptyList(),
            produtosToxicos = emptyList(),
            racaoPetiscos = emptyList(),
            alimentacaoNatural = emptyList(),
            queixa = "",
            conduta = "",
            tratamentos = "",
            retorno = ""
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            TopActionButton("Marcação", Icons.AutoMirrored.Filled.List) {}
            TopActionButton("Produtos", Icons.Default.DateRange) {}
            TopActionButton("Cuidados", Icons.Default.FavoriteBorder) {}
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Meus Pets",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(fakePets) { pet ->
                PetCard(pet, onClick = {})
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
