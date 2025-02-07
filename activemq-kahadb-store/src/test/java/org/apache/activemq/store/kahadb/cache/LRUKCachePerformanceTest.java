package org.apache.activemq.store.kahadb.cache;

import org.apache.activemq.util.LRUCache;
import org.junit.Assert;
import org.junit.Test;

/**
 *  the testHitRate case show the worst case to simple lru case, in which simple cache
 *  hit rate is 0, and lruk cache of that is 1.
 *  That's why lruk should be used in some situation.
 */
public class LRUKCachePerformanceTest {


    /**
     * test mothod:
     *   we prepare insert 2 times cache size data to simple cache and lrucache.
     *   The data was also queryed threahold times when it was pushed to lruk cache.
     *   After that, we query first part of input data (it should be the first half of input data).
     *   The result is that the hit rate of simple lru cache reached 0.But that of lruk cache
     *   is 1.
     *   The test case show the worst case situation for lruk and simple cache.
     */
    @Test
   public void testHitRate(){

        int cacheSize = 500;

        //array size is 1000, 2 times of cachesize
        int[] inputData = { 1282, 5044, 6323, 4900, 8727, 1368, 8496, 3720, 9694, 9328, 2882, 4511, 1616, 3136, 6492, 8367, 3329, 8918, 6070, 3924, 2544, 4136, 9567, 2283, 2805, 917, 5946, 240, 317, 8323, 2063, 2025, 2279, 4187, 513, 6872, 4473, 1183, 8763, 7543, 3600, 8737, 1580, 2874, 7677, 4734, 8730, 1839, 1354, 9718, 2927, 1362, 6365, 6716, 6565, 6427, 4967, 6199, 8051, 2664, 9078, 188, 4217, 5299, 1911, 491, 2923, 4251, 590, 8231, 6830, 9400, 8947, 3617, 2182, 8541, 3581, 1085, 4764, 7371, 9010, 9136, 7954, 6743, 1195, 3077, 2248, 8664, 9349, 7710, 3980, 411, 559, 1819, 4242, 9497, 3107, 1181, 7774, 4554, 9553, 2034, 9794, 1866, 7355, 3400, 2604, 8302, 3623, 5758, 8634, 4500, 5746, 3647, 5155, 4693, 8979, 7864, 6738, 2592, 9107, 143, 9350, 8930, 267, 8501, 3553, 7009, 9944, 1440, 7314, 3748, 8751, 7353, 4563, 4616, 160, 4871, 4666, 5320, 3742, 4602, 8482, 3511, 2349, 4127, 2965, 2645, 4273, 815, 3273, 201, 3923, 2901, 7413, 915, 3506, 8889, 1515, 6204, 3425, 5626, 75, 2149, 6845, 194, 1943, 7974, 992, 6755, 7459, 9764, 8486, 7389, 9451, 8336, 1147, 67, 4934, 7462, 749, 2153, 1290, 2311, 7269, 9722, 9824, 618, 8724, 3902, 9131, 3433, 7227, 1229, 2863, 2782, 7546, 3572, 3042, 8156, 74, 8085, 325, 3126, 9725, 7853, 9543, 8387, 1102, 9192, 1836, 9249, 2263, 180, 8614, 5832, 5279, 1008, 1134, 5803, 1737, 6721, 3299, 1160, 1633, 9975, 224, 9436, 4133, 1618, 3472, 5874, 170, 3341, 4582, 8146, 7855, 4797, 2371, 8874, 4264, 9804, 6538, 8598, 2823, 1727, 193, 8955, 6730, 6600, 4656, 1837, 1061, 1970, 5767, 9955, 6640, 7320, 2720, 6824, 3058, 365, 7655, 9675, 6761, 9773, 3999, 8063, 124, 7001, 455, 3662, 1122, 5542, 197, 5707, 3801, 1505, 3848, 1846, 2713, 4041, 6920, 564, 9476, 968, 6496, 3060, 1605, 1319, 8473, 8451, 1713, 3745, 7503, 3770, 9560, 1432, 5367, 4890, 7505, 3390, 1593, 9541, 3081, 7576, 9278, 3170, 9157, 2407, 9887, 7975, 5644, 7815, 6553, 2546, 8822, 1254, 8342, 2124, 7079, 4763, 9912, 5047, 5587, 2385, 620, 944, 1146, 9587, 3463, 9029, 1005, 3124, 2476, 6279, 9166, 3457, 2866, 6443, 2060, 1976, 2503, 6887, 7685, 5548, 1214, 7671, 1573, 3562, 7069, 9956, 3460, 2019, 8080, 1590, 601, 4074, 5433, 5764, 6054, 1451, 6381, 5343, 7228, 7755, 9431, 8224, 2181, 8170, 6564, 4642, 4569, 2562, 659, 8471, 8975, 5728, 9339, 3240, 1425, 2180, 5863, 2712, 2895, 7688, 6486, 479, 825, 856, 8413, 9048, 8862, 7086, 2938, 7054, 7476, 6001, 9821, 417, 1841, 8758, 579, 8082, 2021, 1329, 1234, 5499, 316, 563, 8968, 4659, 5050, 5608, 6883, 810, 7679, 4925, 3001, 3248, 2445, 5909, 1903, 8448, 9967, 7561, 6322, 9372, 605, 9939, 1553, 1351, 5658, 8088, 6819, 8150, 269, 6853, 8675, 8128, 4265, 8270, 6124, 9342, 5750, 5476, 2083, 3296, 48, 1448, 7138, 7171, 8682, 811, 3219, 7632, 3487, 9795, 2851, 543, 2275, 990, 8523, 7635, 3386, 5049, 5555, 7027, 1418, 9443, 2668, 1355, 8483, 2416, 6098, 2489, 1994, 8961, 2386, 6252, 3799, 3576, 473, 2841, 371, 7581, 5518, 8331, 4724, 1860, 8326, 2701, 6049, 3138, 2118, 2056, 8437, 2205, 1748, 5193, 32, 6678, 4454, 1046, 1262, 7388, 883, 9301, 3867, 2290, 6315, 8055, 7771, 2298, 8418, 3439, 9422, 4514, 8574, 8005, 5634, 976, 936, 5262, 4735, 5087, 204, 3434, 6904, 5316, 2905, 7818, 6809, 8989, 1493, 5357, 1556, 7453, 6444, 6086, 9592, 6302, 2692, 6253, 213, 9376, 5102, 1700, 9059, 8978, 3326, 7775, 6994, 4068, 9752, 1660, 2743, 5023, 1227, 2710, 9492, 3710, 7332, 9681, 5008, 6180, 3442, 4996, 2523, 2265, 6052, 5153, 2909, 3015, 5675, 3140, 5919, 3272, 9321, 5791, 5019, 8310, 8065, 104, 2870, 3102, 1775, 5531, 6343, 7624, 6632, 4886, 9536, 5076, 4081, 1705, 2321, 9751, 1066, 6881, 9572, 5527, 6320, 9614, 3412, 5776, 506, 8062, 6879, 1242, 2260, 1302, 8986, 7102, 35, 6857, 9179, 3179, 6131, 7599, 6022, 3408, 3564, 9333, 349, 8297, 9812, 4448, 9633, 7092, 5903, 4354, 4058, 6752, 6503, 2482, 6695, 9391, 8679, 6473, 4665, 9182, 7542, 7665, 7417, 9767, 6575, 4525, 9888, 8871, 144, 2194, 9002, 1694, 6085, 39, 6518, 9792, 2338, 8842, 3464, 3232, 9748, 9810, 337, 6177, 3724, 5810, 6566, 9345, 7006, 6599, 9427, 8195, 8545, 7648, 9660, 5612, 9187, 9041, 3719, 1311, 9037, 1064, 3692, 1667, 1474, 4221, 2062, 8405, 1224, 7410, 3655, 5393, 5301, 440, 6024, 1223, 5453, 3687, 7653, 4423, 4085, 8488, 7089, 6547, 1917, 6707, 6639, 4469, 2971, 2141, 8303, 3146, 946, 3073, 7578, 209, 1586, 6342, 2578, 6482, 7729, 483, 4923, 7263, 7626, 6325, 6668, 7946, 9209, 1801, 6514, 9389, 6378, 9791, 5733, 2698, 3415, 2832, 1672, 6264, 9230, 2799, 6513, 8479, 1555, 9409, 4345, 1574, 5169, 7088, 5133, 5552, 2014, 4135, 678, 714, 6685, 7713, 6409, 4470, 7422, 9808, 7997, 318, 9602, 7322, 9702, 6357, 1916, 7973, 2551, 6540, 8561, 3977, 637, 5714, 3239, 8435, 2013, 9941, 3483, 1295, 6068, 225, 7404, 9600, 6312, 3853, 5765, 3904, 243, 8849, 6366, 4498, 2292, 8964, 589, 9394, 3558, 9848, 9503, 1615, 175, 2188, 1682, 2165, 7081, 5105, 2331, 8970, 7381, 4676, 794, 1791, 554, 1789, 56, 4297, 6578, 2378, 3422, 6481, 8550, 2023, 7425, 909, 306, 1921, 6932, 4431, 9838, 1891, 1031, 496, 422, 2545, 8359, 7123, 6349, 6465, 1073, 9845, 6723, 6733, 7333, 7717, 4922, 8832, 30, 5415, 7463, 6418, 5429, 5196, 6058, 2256, 4325, 4605, 1055, 736, 1595, 4278, 3477, 6760, 352, 3125, 2220, 3660, 7966, 7141, 5448, 4302, 1178, 5227, 7188, 7375, 5930, 3942, 4827, 7923, 4891, 1655, 7226, 537, 4227, 2501, 7032, 3134, 9731, 6662, 2885, 8317, 9404, 1464, 7072, 4204, 9786, 6431, 6557, 9474, 1164, 4848, 4885, 8828, 644, 7360, 3810, 5359, 1499, 4013, 5980, 8509, 8757, 6595, 7031, 6010, 3919, 5950, 6114, 6598, 9501, 4730, 7674, 2640, 3877, 125, 7601, 8858, 8104, 7832, 9696, 823, 7922, 681, 9925, 2529, 8538, 2134, 5498, 1241, 1995, 5391, 3899, 4998, 3320, 7065, 9188, 6169, 5819, 40, 3888, 8228, 6196, 8793, 8882, 6121, 8245, 5395, 1013, 8644, 1422, 5990, 7750, 2225, 6382, 4975, 7377, 6026, 7429, 9161, 1208, 2463, 4289, 5258, 5059, 9515, 1919, 9019, 3828, 3590, 8438, 408, 872, 8271, 2889, 9668, 3336, 1795, 8780, 6597, 9415, 2457, 7797, 1833, 5875, 1097, 9275, 621 };
        LRUKCache<Integer, Integer> lrukCache = new LRUKCache<>(cacheSize, 2);
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(cacheSize);

        //caculate simple lru cache hit rate
        for (int i = 0; i < inputData.length; i++) {
            lruCache.put(inputData[i], inputData[i]);
        }

        //we query first 500 array data here to get hit rate for simple lru cache

        // hit number for simple lru cache. We queryd 500 times (alse equeal cache size) here,
        // so the total number is 500
        double hitNum = 0;
        int totalQueryTimes = cacheSize;
        for (int i = 0; i < cacheSize; i++) {
            Integer nums = lruCache.get(inputData[i]);
            if(nums != null){
                hitNum++;
            }
        }

        double hitRateForSimpleCache = hitNum / totalQueryTimes;

        //reset hitnumber = 0
        hitNum = 0;
        //caculate lruk cache hit rate

        //first prepare push data to lruk cache
        for (int i = 0; i < inputData.length; i++) {
            lrukCache.put(inputData[i], inputData[i]);
            //after push data to lruk cache, we query it threshold times.
            int threshold = 5;
            if(i <= cacheSize){
                while (threshold > 0){
                    lrukCache.get(inputData[i]);
                    threshold--;
                }
            }
        }

        //we query first 500 array data here to get hit rate for lruk cache.
        hitNum = 0;
        for (int i = 0; i < cacheSize; i++) {
            Integer nums = lrukCache.get(inputData[i]);
            if(nums != null){
                hitNum++;
            }
        }

        double hitRateForSLRUKCache = hitNum / 500;
        Assert.assertEquals(hitRateForSimpleCache, 0, 0);
        Assert.assertEquals(hitRateForSLRUKCache, 1, 0);

   }

}
