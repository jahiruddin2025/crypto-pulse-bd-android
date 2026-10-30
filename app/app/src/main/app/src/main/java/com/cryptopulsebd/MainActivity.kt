
package com.cryptopulsebd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF0B1020)
private val Panel = Color(0xFF151D30)
private val Green = Color(0xFF00D69A)
private val Red = Color(0xFFFF5570)
private val White = Color(0xFFF4F6FC)
private val Muted = Color(0xFF8994AA)

data class Coin(
    val name: String,
    val symbol: String,
    val price: String,
    val change: String,
    val positive: Boolean
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoPulseApp()
        }
    }
}

@Composable
fun CryptoPulseApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var search by remember { mutableStateOf("") }

    val coins = listOf(
        Coin("Bitcoin", "BTC", "$110,250.00", "+2.45%", true),
        Coin("Ethereum", "ETH", "$3,850.00", "+1.82%", true),
        Coin("Solana", "SOL", "$185.40", "-0.75%", false),
        Coin("XRP", "XRP", "$2.45", "+3.20%", true),
        Coin("BNB", "BNB", "$980.00", "+0.65%", true),
        Coin("Cardano", "ADA", "$0.72", "-1.15%", false)
    )

    val filteredCoins = coins.filter {
        it.name.contains(search, ignoreCase = true) ||
        it.symbol.contains(search, ignoreCase = true)
    }

    MaterialTheme {
        Scaffold(
            containerColor = Bg,
            bottomBar = {
                NavigationBar(containerColor = Panel) {
                    val labels = listOf("Home", "Markets", "News", "Portfolio")
                    val icons = listOf(
                        Icons.Default.Home,
                        Icons.Default.ShowChart,
                        Icons.Default.Article,
                        Icons.Default.AccountBalanceWallet
                    )

                    labels.forEachIndexed { index, label ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            icon = { Icon(icons[index], contentDescription = label) },
                            label = { Text(label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Green,
                                selectedTextColor = Green,
                                unselectedIconColor = Muted,
                                unselectedTextColor = Muted,
                                indicatorColor = Bg
                            )
                        )
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Bg)
                    .padding(padding)
                    .padding(horizontal = 18.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp, bottom = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "CRYPTO PULSE",
                            color = Green,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "BD",
                            color = White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = White
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.Language,
                            contentDescription = "Language",
                            tint = White
                        )
                    }
                }

                if (selectedTab == 0 || selectedTab == 1) {
                    MarketDashboard(
                        coins = filteredCoins,
                        search = search,
                        onSearchChange = { search = it }
                    )
                } else if (selectedTab == 2) {
                    Text(
                        "Crypto News",
                        color = White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                    Text(
                        "News feature will be connected in the next step.",
                        color = Muted
                    )
                } else {
                    Text(
                        "My Portfolio",
                        color = White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                    Text(
                        "Portfolio feature will be added in the next step.",
                        color = Muted
                    )
                }
            }
        }
    }
}

@Composable
fun MarketDashboard(
    coins: List<Coin>,
    search: String,
    onSearchChange: (String) -> Unit
) {
    Column {
        Text(
            "Market Overview",
            color = White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Your crypto market dashboard",
            color = Muted,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Panel),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text("Total Crypto Market Cap", color = Muted)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "$3.85T",
                    color = White,
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "+2.35% today",
                    color = Green,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = search,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search coins...", color = Muted) },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Muted
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = White,
                unfocusedTextColor = White,
                focusedBorderColor = Green,
                unfocusedBorderColor = Panel,
                cursorColor = Green,
                focusedContainerColor = Panel,
                unfocusedContainerColor = Panel
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            "Top Cryptocurrencies",
            color = White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            items(coins) { coin ->
                CoinRow(coin)
            }
        }
    }
}

@Composable
fun CoinRow(coin: Coin) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Panel),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    coin.name,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Text(
                    coin.symbol,
                    color = Muted,
                    fontSize = 12.sp
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    coin.price,
                    color = White,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    coin.change,
                    color = if (coin.positive) Green else Red,
                    fontSize = 12.sp
                )
            }
        }
    }
}
