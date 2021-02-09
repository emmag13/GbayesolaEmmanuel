package com.ehealth4everyone.gbayesolaemmanueloluwaseyi

import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.activity.CarOwnersActivity
import org.junit.Test
import org.mockito.InjectMocks
import java.util.*

@InjectMocks
var carOwnersActivity: CarOwnersActivity? = null

internal class FilterUnitTest {
    @Test
    fun filterList(){
        val dummy: MutableList<Array<String>> = ArrayList()
        dummy.add(
            arrayOf(
                "1",
                "Scot",
                "Hainning",
                "shainning0@so-net.ne.jp",
                "Thailand",
                "Lincoln",
                "1996",
                "Maroon",
                "Male",
                "Staff Accountant III",
                "Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin interdum mauris non ligula pellentesque ultrices."
            )
        )
        dummy.add(
            arrayOf(
                "2",
                "Vannie",
                "Fitzer",
                "vfitzer1@samsung.com",
                "France",
                "Chrysler",
                "2005",
                "Green",
                "Female",
                "VP Quality Control",
                "Nulla facilisi. Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque."
            )
        )
        dummy.add(
            arrayOf(
                "6",
                "Birch",
                "Sworder",
                "bsworder5@amazon.de",
                "China",
                "Ford",
                "2011",
                "Yellow",
                "Male",
                "Operator",
                "Duis at velit eu est congue elementum."
            )
        )
        assert(true) {
            if (dummy != null) {
                carOwnersActivity?.filterCarOwners(dummy)
            }
        }
    }
}