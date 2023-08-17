package com.rezyfr.cryptoapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rezyfr.cryptoapp.domain.model.CryptoFeed

@Composable
fun CryptoFeedList(
    cryptoFeed: List<CryptoFeed>,
    navigateToDetails: (CryptoFeed) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(cryptoFeed) { cryptoFeed ->
            CryptoFeedItem(
                cryptoFeed = cryptoFeed,
                onCryptoFeedClick = navigateToDetails
            )
        }
    }
}
@Composable
fun CryptoFeedItem(
    cryptoFeed: CryptoFeed,
    onCryptoFeedClick: (CryptoFeed) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCryptoFeedClick(cryptoFeed)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(2f)
            ) {
                Box(
                    modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .background(Color(0xFFFCFCFF))
                        .size(50.dp)
                ) {
                    AsyncImage(
                        model = "https://cryptocompare.com/${cryptoFeed.coinInfo.imageUrl}",
                        contentDescription = "Crypto Image",
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(5f)
            ) {
                Text(
                    text = cryptoFeed.coinInfo.fullName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = cryptoFeed.coinInfo.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    textAlign = TextAlign.Start
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(5f)
            ) {
                Text(
                    text = "$${cryptoFeed.display.usd.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )

                Text(
                    text = "${cryptoFeed.display.usd.changePctDay}%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (cryptoFeed.display.usd.changePctDay < 0) Color.Red else Color.Green,
                )
            }
        }
        Divider(modifier = Modifier.background(Color.Gray))
    }
}