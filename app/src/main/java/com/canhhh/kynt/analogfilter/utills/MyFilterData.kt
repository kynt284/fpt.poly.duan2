package com.canhhh.kynt.analogfilter.utills


import android.content.Context
import com.zomato.photofilters.geometry.Point
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.subfilters.*
import java.util.*


object MyFilterData {

    fun getFilterPack(context: Context): List<Filter> {
        val filters = ArrayList<Filter>()
        filters.add(filter1())
        filters.add(filter2(context))
        filters.add(filter3(context))
        filters.add(filter4(context))
        filters.add(filter5())
        filters.add(filter6(context))
        filters.add(filter7())
        filters.add(filter8())
        filters.add(filter9())
        filters.add(filter10())
        filters.add(filter11())
        filters.add(filter12())
        filters.add(filter13())
        filters.add(filter14())
        filters.add(filter15())
        return filters
    }



    private fun filter15(): Filter {
        val rgbKnots: Array<Point?> = arrayOfNulls(4)
        rgbKnots[0] = Point(0f, 0f)
        rgbKnots[1] = Point(24f, 12f)
        rgbKnots[2] = Point(78f, 210f)
        rgbKnots[3] = Point(255f, 255f)
        val filter = Filter()
        filter.name = "F15"
        filter.addSubFilter(BrightnessSubFilter(8))
        filter.addSubFilter(ToneCurveSubFilter(rgbKnots, null, null, null))
        return filter
    }


    private fun filter14(): Filter {
        val rgbKnots: Array<Point?> = arrayOfNulls(12)
        rgbKnots[0] = Point(0f, 0f)
        rgbKnots[1] = Point(24f, 3f)
        rgbKnots[2] = Point(55f, 20f)
        rgbKnots[3] = Point(90f, 54f)
        rgbKnots[4] = Point(132f, 99f)
        rgbKnots[5] = Point(145f, 112f)
        rgbKnots[6] = Point(189f, 195f)
        rgbKnots[7] = Point(198f, 210f)
        rgbKnots[8] = Point(212f, 222f)
        rgbKnots[9] = Point(232f, 235f)
        rgbKnots[10] = Point(245f, 240f)
        rgbKnots[11] = Point(255f, 255f)
        val filter = Filter()
        filter.name = "F14"
        filter.addSubFilter(ContrastSubFilter(2f))
        filter.addSubFilter(BrightnessSubFilter(15))
        filter.addSubFilter(ToneCurveSubFilter(rgbKnots, null, null, null))
        return filter
    }


    private fun filter13(): Filter {
        val redKnots: Array<Point?> = arrayOfNulls(4)
        val greenKnots: Array<Point?> = arrayOfNulls(5)
        val blueKnots: Array<Point?> = arrayOfNulls(6)

        redKnots[0] = Point(0f, 0f)
        redKnots[1] = Point(56f, 68f)
        redKnots[2] = Point(196f, 206f)
        redKnots[3] = Point(255f, 255f)


        greenKnots[0] = Point(0f, 0f)
        greenKnots[1] = Point(25f, 66f)
        greenKnots[2] = Point(94f, 115f)
        greenKnots[3] = Point(167f, 168f)
        greenKnots[4] = Point(255f, 255f)


        blueKnots[0] = Point(0f, 0f)
        blueKnots[1] = Point(33f, 86f)
        blueKnots[2] = Point(126f, 170f)
        blueKnots[3] = Point(156f, 206f)
        blueKnots[4] = Point(199f, 225f)
        blueKnots[5] = Point(255f, 255f)

        val filter = Filter("F13")
        filter.addSubFilter(ContrastSubFilter(1.0f))
        filter.addSubFilter(BrightnessSubFilter(10))
        filter.addSubFilter(ToneCurveSubFilter(null, redKnots, greenKnots, blueKnots))
        return filter
    }


    private fun filter12(): Filter {
        val rgbKnots: Array<Point?> = arrayOfNulls(8)
        rgbKnots[0] = Point(0f, 0f)
        rgbKnots[1] = Point(34f, 6f)
        rgbKnots[2] = Point(69f, 23f)
        rgbKnots[3] = Point(100f, 58f)
        rgbKnots[4] = Point(150f, 154f)
        rgbKnots[5] = Point(176f, 196f)
        rgbKnots[6] = Point(207f, 233f)
        rgbKnots[7] = Point(255f, 255f)
        val filter = Filter()
        filter.name = "F12"
        filter.addSubFilter(ToneCurveSubFilter(rgbKnots, null, null, null))
        return filter
    }

    private fun filter11(): Filter {
        val redKnots: Array<Point?> = arrayOfNulls(8)
        redKnots[0] = Point(0f, 0f)
        redKnots[1] = Point(86f, 34f)
        redKnots[2] = Point(117f, 41f)
        redKnots[3] = Point(146f, 80f)
        redKnots[4] = Point(170f, 151f)
        redKnots[5] = Point(200f, 214f)
        redKnots[6] = Point(225f, 242f)
        redKnots[7] = Point(255f, 255f)
        val filter = Filter()
        filter.name = "F11"
        filter.addSubFilter(ToneCurveSubFilter(null, redKnots, null, null))
        filter.addSubFilter(BrightnessSubFilter(30))
        filter.addSubFilter(ContrastSubFilter(1f))
        return filter
    }

    private fun filter10(): Filter {
        val rgbKnots: Array<Point?> = arrayOfNulls(5)
        val redKnots: Array<Point?> = arrayOfNulls(5)
        val greenKnots: Array<Point?> = arrayOfNulls(6)
        val blueKnots: Array<Point?> = arrayOfNulls(7)

        rgbKnots[0] = Point(0f, 0f)
        rgbKnots[1] = Point(80f, 43f)
        rgbKnots[2] = Point(149f, 102f)
        rgbKnots[3] = Point(201f, 173f)
        rgbKnots[4] = Point(255f, 255f)

        redKnots[0] = Point(0f, 0f)
        redKnots[1] = Point(125f, 147f)
        redKnots[2] = Point(177f, 199f)
        redKnots[3] = Point(213f, 228f)
        redKnots[4] = Point(255f, 255f)


        greenKnots[0] = Point(0f, 0f)
        greenKnots[1] = Point(57f, 76f)
        greenKnots[2] = Point(103f, 130f)
        greenKnots[3] = Point(167f, 192f)
        greenKnots[4] = Point(211f, 229f)
        greenKnots[5] = Point(255f, 255f)


        blueKnots[0] = Point(0f, 0f)
        blueKnots[1] = Point(38f, 62f)
        blueKnots[2] = Point(75f, 112f)
        blueKnots[3] = Point(116f, 158f)
        blueKnots[4] = Point(171f, 204f)
        blueKnots[5] = Point(212f, 233f)
        blueKnots[6] = Point(255f, 255f)

        val filter = Filter()
        filter.name = "F10"
        filter.addSubFilter(ToneCurveSubFilter(rgbKnots, redKnots, greenKnots, blueKnots))
        return filter
    }

    private fun filter9(): Filter {
        val blueKnots: Array<Point?> = arrayOfNulls(3)
        blueKnots[0] = Point(0f, 0f)
        blueKnots[1] = Point(165f, 114f)
        blueKnots[2] = Point(255f, 255f)
        val filter = Filter()
        filter.name = "F9"
        filter.addSubFilter(ToneCurveSubFilter(null, null, null, blueKnots))
        return filter
    }

    private fun filter8(): Filter {
        val rgbKnots: Array<Point?> = arrayOfNulls(3)
        val redKnots: Array<Point?> = arrayOfNulls(4)
        val greenKnots: Array<Point?> = arrayOfNulls(3)
        val blueKnots: Array<Point?> = arrayOfNulls(3)

        rgbKnots[0] = Point(0f, 0f)
        rgbKnots[1] = Point(174f, 109f)
        rgbKnots[2] = Point(255f, 255f)

        redKnots[0] = Point(0f, 0f)
        redKnots[1] = Point(70f, 114f)
        redKnots[2] = Point(157f, 145f)
        redKnots[3] = Point(255f, 255f)

        greenKnots[0] = Point(0f, 0f)
        greenKnots[1] = Point(109f, 138f)
        greenKnots[2] = Point(255f, 255f)

        blueKnots[0] = Point(0f, 0f)
        blueKnots[1] = Point(113f, 152f)
        blueKnots[2] = Point(255f, 255f)

        val filter = Filter()
        filter.name = "F8"
        filter.addSubFilter(ContrastSubFilter(1.5f))
        filter.addSubFilter(ToneCurveSubFilter(rgbKnots, redKnots, greenKnots, blueKnots))
        return filter
    }

    private fun filter7(): Filter {
        val blueKnots: Array<Point?> = arrayOfNulls(6)
        blueKnots[0] = Point(0f, 0f)
        blueKnots[1] = Point(11f, 40f)
        blueKnots[2] = Point(36f, 99f)
        blueKnots[3] = Point(86f, 151f)
        blueKnots[4] = Point(167f, 209f)
        blueKnots[5] = Point(255f, 255f)
        val filter = Filter("F7")
        filter.addSubFilter(ContrastSubFilter(1.2f))
        filter.addSubFilter(ToneCurveSubFilter(null, null, null, blueKnots))
        return filter
    }


    private fun filter6(context: Context): Filter {
        val blueKnots: Array<Point?> = arrayOfNulls(4)
        val redKnots: Array<Point?> = arrayOfNulls(4)

        blueKnots[0] = Point(0f, 0f)
        blueKnots[1] = Point(39f, 70f)
        blueKnots[2] = Point(150f, 200f)
        blueKnots[3] = Point(255f, 255f)

        redKnots[0] = Point(0f, 0f)
        redKnots[1] = Point(45f, 64f)
        redKnots[2] = Point(170f, 190f)
        redKnots[3] = Point(255f, 255f)

        val filter = Filter("F6")
        filter.addSubFilter(ContrastSubFilter(1.9f))
        filter.addSubFilter(BrightnessSubFilter(60))
        filter.addSubFilter(VignetteSubfilter(context, 200))
        filter.addSubFilter(ToneCurveSubFilter(null, redKnots, null, blueKnots))
        return filter
    }

    private fun filter5(): Filter {
        val filter = Filter("F5")
        filter.addSubFilter(ContrastSubFilter(1.5f))
        filter.addSubFilter(BrightnessSubFilter(10))
        return filter
    }

    private fun filter4(context: Context): Filter {
        val blueKnots: Array<Point?> = arrayOfNulls(4)
        val redKnots: Array<Point?> = arrayOfNulls(4)

        blueKnots[0] = Point(0f, 0f)
        blueKnots[1] = Point(39f, 70f)
        blueKnots[2] = Point(150f, 200f)
        blueKnots[3] = Point(255f, 255f)

        redKnots[0] = Point(0f, 0f)
        redKnots[1] = Point(45f, 64f)
        redKnots[2] = Point(170f, 190f)
        redKnots[3] = Point(255f, 255f)

        val filter = Filter("F4")
        filter.addSubFilter(ContrastSubFilter(1.5f))
        filter.addSubFilter(BrightnessSubFilter(5))
        filter.addSubFilter(VignetteSubfilter(context, 150))
        filter.addSubFilter(ToneCurveSubFilter(null, redKnots, null, blueKnots))
        return filter
    }

    private fun filter3(context: Context): Filter {
        val greenKnots: Array<Point?> = arrayOfNulls(3)
        greenKnots[0] = Point(0f, 0f)
        greenKnots[1] = Point(113f, 142f)
        greenKnots[2] = Point(255f, 255f)

        val filter = Filter("F3")
        filter.addSubFilter(ContrastSubFilter(1.3f))
        filter.addSubFilter(BrightnessSubFilter(60))
        filter.addSubFilter(VignetteSubfilter(context, 200))
        filter.addSubFilter(ToneCurveSubFilter(null, null, greenKnots, null))
        return filter
    }

    private fun filter2(context: Context): Filter {
        val filter = Filter("F2")
        filter.addSubFilter(BrightnessSubFilter(30))
        filter.addSubFilter(SaturationSubfilter(0.8f))
        filter.addSubFilter(ContrastSubFilter(1.3f))
        filter.addSubFilter(VignetteSubfilter(context, 100))
        filter.addSubFilter(ColorOverlaySubFilter(100, .2f, .2f, .1f))
        return filter
    }

    private fun filter1(): Filter {
        val redKnots: Array<Point?> = arrayOfNulls(4)
        val greenKnots: Array<Point?> = arrayOfNulls(4)
        val blueKnots: Array<Point?> = arrayOfNulls(4)

        redKnots[0] = Point(0f, 0f)
        redKnots[1] = Point(56f, 68f)
        redKnots[2] = Point(196f, 206f)
        redKnots[3] = Point(255f, 255f)


        greenKnots[0] = Point(0f, 0f)
        greenKnots[1] = Point(46f, 77f)
        greenKnots[2] = Point(160f, 200f)
        greenKnots[3] = Point(255f, 255f)


        blueKnots[0] = Point(0f, 0f)
        blueKnots[1] = Point(33f, 86f)
        blueKnots[2] = Point(126f, 220f)
        blueKnots[3] = Point(255f, 255f)

        val filter = Filter("F1")
        filter.addSubFilter(ContrastSubFilter(1.5f))
        filter.addSubFilter(BrightnessSubFilter(-10))
        filter.addSubFilter(ToneCurveSubFilter(null, redKnots, greenKnots, blueKnots))
        return filter
    }
}
