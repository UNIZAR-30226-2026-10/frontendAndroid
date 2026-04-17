package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.fakes

import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.CasillaSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TableroSnapshot
import com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.domain.model.TipoCasilla


val fakeTableroSnapshot = TableroSnapshot(

    casillas = (1..100).map { n ->
            data class CasillaInfo( // Clase auxiliar para devolver el tipo de casilla
                val tipo: TipoCasilla,
                val salto: Int?,
                val siguientes: List<Int>,
                val rotacion: Int = 90, // Valor por defecto
                val esCurva: Boolean = false // Valor por defecto
            )

            val (tipo, salto, siguientes, rotacion, esCurva) = when (n) {
                // Inicial
                1 -> CasillaInfo(TipoCasilla.Normal, null, listOf(2))

                // Escaleras
                7 -> CasillaInfo(TipoCasilla.Escalera, 26, listOf(8))
                15 -> CasillaInfo(TipoCasilla.Escalera, 31, listOf(14))
                37 -> CasillaInfo(TipoCasilla.Escalera, 98, listOf(36))
                51 -> CasillaInfo(TipoCasilla.Escalera, 73, listOf(72), 270, true)
                65 -> CasillaInfo(TipoCasilla.Escalera, 84, listOf(75), 270, true)

                // Serpientes
                17 -> CasillaInfo(TipoCasilla.Serpiente, 9, listOf(16))
                68 -> CasillaInfo(TipoCasilla.Serpiente, 19, listOf(67))
                99 -> CasillaInfo(TipoCasilla.Serpiente, 25, listOf(100))
                54 -> CasillaInfo(TipoCasilla.Serpiente, 43, listOf(53))
                83 -> CasillaInfo(TipoCasilla.Serpiente, 56, listOf(84))

                // Bifurcaciones
                56 -> CasillaInfo(TipoCasilla.Bifurcacion, null, listOf(55, 57), 270)
                95 -> CasillaInfo(TipoCasilla.Bifurcacion, null, listOf(96), 270)

                // Curvas
                10 -> CasillaInfo(TipoCasilla.Normal, null, listOf(20), 180, true)
                20 -> CasillaInfo(TipoCasilla.Normal, null, listOf(19), 90, true)
                11 -> CasillaInfo(TipoCasilla.Normal, null, listOf(21), 270, true)
                21 -> CasillaInfo(TipoCasilla.Normal, null, listOf(22), 0, true)
                30 -> CasillaInfo(TipoCasilla.Normal, null, listOf(40), 180, true)
                40 -> CasillaInfo(TipoCasilla.Normal, null, listOf(39), 90, true)
                31 -> CasillaInfo(TipoCasilla.Normal, null, listOf(41), 270, true)
                41 -> CasillaInfo(TipoCasilla.Normal, null, listOf(42), 0, true)
                46 -> CasillaInfo(TipoCasilla.Normal, null, listOf(56), 180, true)
                61 -> CasillaInfo(TipoCasilla.Normal, null, listOf(62), 0, true)
                60 -> CasillaInfo(TipoCasilla.Normal, null, listOf(70), 180, true)
                70 -> CasillaInfo(TipoCasilla.Normal, null, listOf(69), 90, true)
                64 -> CasillaInfo(TipoCasilla.Normal, null, listOf(74), 180, true)
                74 -> CasillaInfo(TipoCasilla.Normal, null, listOf(73), 90, true)
                75 -> CasillaInfo(TipoCasilla.Normal, null, listOf(76), 0, true)
                84 -> CasillaInfo(TipoCasilla.Normal, null, listOf(94), 180, true)
                94 -> CasillaInfo(TipoCasilla.Normal, null, listOf(95), 0, true)
                85 -> CasillaInfo(TipoCasilla.Normal, null, listOf(95), 270, true)
                71 -> CasillaInfo(TipoCasilla.Normal, null, listOf(81), 270, true)
                81 -> CasillaInfo(TipoCasilla.Normal, null, listOf(82), 0, true)
                80 -> CasillaInfo(TipoCasilla.Normal, null, listOf(90), 180, true)
                90 -> CasillaInfo(TipoCasilla.Normal, null, listOf(89), 90, true)




                // Meta
                100 -> CasillaInfo(TipoCasilla.Meta, null, emptyList<Int>())

                // Normal
                2 -> CasillaInfo(TipoCasilla.Normal, null, listOf(3))
                3 -> CasillaInfo(TipoCasilla.Normal, null, listOf(4))
                4 -> CasillaInfo(TipoCasilla.Normal, null, listOf(5))
                5 -> CasillaInfo(TipoCasilla.Normal, null, listOf(6))
                6 -> CasillaInfo(TipoCasilla.Normal, null, listOf(7))
                8 -> CasillaInfo(TipoCasilla.Normal, null, listOf(9))
                9 -> CasillaInfo(TipoCasilla.Normal, null, listOf(10))

                12 -> CasillaInfo(TipoCasilla.Normal, null, listOf(11))
                13 -> CasillaInfo(TipoCasilla.Normal, null, listOf(12))
                14 -> CasillaInfo(TipoCasilla.Normal, null, listOf(13))
                16 -> CasillaInfo(TipoCasilla.Normal, null, listOf(15))
                18 -> CasillaInfo(TipoCasilla.Normal, null, listOf(17))
                19 -> CasillaInfo(TipoCasilla.Normal, null, listOf(18))

                22 -> CasillaInfo(TipoCasilla.Normal, null, listOf(23))
                23 -> CasillaInfo(TipoCasilla.Normal, null, listOf(24))
                24 -> CasillaInfo(TipoCasilla.Normal, null, listOf(25))
                25 -> CasillaInfo(TipoCasilla.Normal, null, listOf(26))
                26 -> CasillaInfo(TipoCasilla.Normal, null, listOf(27))
                27 -> CasillaInfo(TipoCasilla.Normal, null, listOf(28))
                28 -> CasillaInfo(TipoCasilla.Normal, null, listOf(29))
                29 -> CasillaInfo(TipoCasilla.Normal, null, listOf(30))

                32 -> CasillaInfo(TipoCasilla.Normal, null, listOf(31))
                33 -> CasillaInfo(TipoCasilla.Normal, null, listOf(32))
                34 -> CasillaInfo(TipoCasilla.Normal, null, listOf(33))
                35 -> CasillaInfo(TipoCasilla.Normal, null, listOf(34))
                36 -> CasillaInfo(TipoCasilla.Normal, null, listOf(35))
                38 -> CasillaInfo(TipoCasilla.Normal, null, listOf(37))
                39 -> CasillaInfo(TipoCasilla.Normal, null, listOf(38))

                42 -> CasillaInfo(TipoCasilla.Normal, null, listOf(43))
                43 -> CasillaInfo(TipoCasilla.Normal, null, listOf(44))
                44 -> CasillaInfo(TipoCasilla.Normal, null, listOf(45))
                45 -> CasillaInfo(TipoCasilla.Normal, null, listOf(46))

                52 -> CasillaInfo(TipoCasilla.Normal, null, listOf(51))
                53 -> CasillaInfo(TipoCasilla.Normal, null, listOf(52))
                55 -> CasillaInfo(TipoCasilla.Normal, null, listOf(54))

                62 -> CasillaInfo(TipoCasilla.Normal, null, listOf(63))
                63 -> CasillaInfo(TipoCasilla.Normal, null, listOf(64))

                72 -> CasillaInfo(TipoCasilla.Normal, null, listOf(71))
                73 -> CasillaInfo(TipoCasilla.Normal, null, listOf(72))

                82 -> CasillaInfo(TipoCasilla.Normal, null, listOf(83))

                57 -> CasillaInfo(TipoCasilla.Normal, null, listOf(58))
                58 -> CasillaInfo(TipoCasilla.Normal, null, listOf(59))
                59 -> CasillaInfo(TipoCasilla.Normal, null, listOf(60))

                69 -> CasillaInfo(TipoCasilla.Normal, null, listOf(68))
                67 -> CasillaInfo(TipoCasilla.Normal, null, listOf(66))
                66 -> CasillaInfo(TipoCasilla.Normal, null, listOf(65))

                76 -> CasillaInfo(TipoCasilla.Normal, null, listOf(77))
                77 -> CasillaInfo(TipoCasilla.Normal, null, listOf(78))
                78 -> CasillaInfo(TipoCasilla.Normal, null, listOf(79))
                79 -> CasillaInfo(TipoCasilla.Normal, null, listOf(80))

                86 -> CasillaInfo(TipoCasilla.Normal, null, listOf(85))
                87 -> CasillaInfo(TipoCasilla.Normal, null, listOf(86))
                88 -> CasillaInfo(TipoCasilla.Normal, null, listOf(87))
                89 -> CasillaInfo(TipoCasilla.Normal, null, listOf(88))

                96 -> CasillaInfo(TipoCasilla.Normal, null, listOf(97))
                97 -> CasillaInfo(TipoCasilla.Normal, null, listOf(98))
                98 -> CasillaInfo(TipoCasilla.Normal, null, listOf(99))

                else -> CasillaInfo(TipoCasilla.Vacio, null, listOf(1))
            }

            CasillaSnapshot(
                esCurva = esCurva,
                rotacion = rotacion,
                tipo = tipo,
                siguientes = siguientes,
                saltoA = salto,
            )
        }
)