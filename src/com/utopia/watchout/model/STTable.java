
package com.utopia.watchout.model;

import android.content.Context;

import com.utopia.watchout.R;
import com.utopia.watchout.helpers.ReadExcelHelper;

import java.text.Collator;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

public class STTable {
    public static Province[] ProvinceTable = null;
    static int[] numLocals = {
            26, 17, 9, 13, 6, 6, 6, 2, 32, 19, 13, 16, 15, 23, 24, 19, 3
    };
    static int[][] seoulStats = new int[numLocals[0]][5];
    static int[][] busanStats = new int[numLocals[1]][5];
    static int[][] daeguStats = new int[numLocals[2]][5];
    static int[][] incheonStats = new int[numLocals[3]][5];
    static int[][] gwangjuStats = new int[numLocals[4]][5];
    static int[][] daejeonStats = new int[numLocals[5]][5];
    static int[][] ulsanStats = new int[numLocals[6]][5];
    static int[][] sejongStats = new int[numLocals[7]][5];
    static int[][] gyeonggiStats = new int[numLocals[8]][5];
    static int[][] gangwonStats = new int[numLocals[9]][5];
    static int[][] chungbukStats = new int[numLocals[10]][5];
    static int[][] chungnamStats = new int[numLocals[11]][5];
    static int[][] jeonbukStats = new int[numLocals[12]][5];
    static int[][] jeonnamStats = new int[numLocals[13]][5];
    static int[][] gyeongbukStats = new int[numLocals[14]][5];
    static int[][] gyeongnamStats = new int[numLocals[15]][5];
    static int[][] jejuStats = new int[numLocals[16]][5];

    public static int[] parsingValues(Context context, String localname, int numdiv) {
        // parsing values from excel
        ReadExcelHelper infile = new ReadExcelHelper(context, localname);
        String mTargetAge;

        int[] stats = new int[5];
        for (int j = 1; j <= 5; j++) {
            mTargetAge = infile.getCell(j, numdiv);
            int num = Integer.parseInt(mTargetAge);
            stats[j - 1] = num;
        }
        return stats;
    }

    public static void makeProvTable(Context context) {
        ProvinceTable = new Province[18];
        ProvinceTable[0] = new Province(context, ProvinceType.SouthKorea);
        ProvinceTable[1] = new Province(context, ProvinceType.Seoul);
        ProvinceTable[2] = new Province(context, ProvinceType.Busan);
        ProvinceTable[3] = new Province(context, ProvinceType.Daegu);
        ProvinceTable[4] = new Province(context, ProvinceType.Incheon);
        ProvinceTable[5] = new Province(context, ProvinceType.Gwangju);
        ProvinceTable[6] = new Province(context, ProvinceType.Daejeon);
        ProvinceTable[7] = new Province(context, ProvinceType.Ulsan);
        ProvinceTable[8] = new Province(context, ProvinceType.SeJong);
        ProvinceTable[9] = new Province(context, ProvinceType.Gyeonggi);
        ProvinceTable[10] = new Province(context, ProvinceType.Gangwon);
        ProvinceTable[11] = new Province(context, ProvinceType.Chungcheongbuk);
        ProvinceTable[12] = new Province(context, ProvinceType.Chungcheongnam);
        ProvinceTable[13] = new Province(context, ProvinceType.Jeollabuk);
        ProvinceTable[14] = new Province(context, ProvinceType.Jeollanam);
        ProvinceTable[15] = new Province(context, ProvinceType.Gyeongsangbuk);
        ProvinceTable[16] = new Province(context, ProvinceType.Gyeongsangnam);
        ProvinceTable[17] = new Province(context, ProvinceType.Jeju);
    }

    public static Province getProvince(Context context, String name) {
        if (ProvinceTable == null)
            makeProvTable(context);

        for (Province prov : ProvinceTable)
            if (prov.getName() != null && prov.getName().equals(name))
                return prov;
        return null;
    }

    public enum ProvinceType {
        SouthKorea,
        Seoul,
        Busan,
        Daegu,
        Incheon,
        Gwangju,
        Daejeon,
        Ulsan,
        SeJong,
        Gyeonggi,
        Gangwon,
        Chungcheongbuk,
        Chungcheongnam,
        Jeollabuk,
        Jeollanam,
        Gyeongsangbuk,
        Gyeongsangnam,
        Jeju,
        NULL
    }

    public static class Province {

        private ProvinceType mType = ProvinceType.NULL;
        private String mName = null;
        private Local[] mLocalList = null;
        private String[] mLocalNameList = null;

        public Province(Context context, ProvinceType type) {
            mType = type;
            String[] provinceList = context.getResources().getStringArray(R.array.province_list);
            switch (type) {
                case SouthKorea:
                    mName = (provinceList[0]);
                    mLocalList = makeLocalList(context, ProvinceType.SouthKorea);
                    break;
                case Seoul:
                    mName = (provinceList[1]);
                    mLocalList = makeLocalList(context, ProvinceType.Seoul);
                    mLocalNameList = context.getResources().getStringArray(R.array.seoul_list);
                    break;
                case Busan:
                    mName = (provinceList[2]);
                    mLocalList = makeLocalList(context, ProvinceType.Busan);
                    mLocalNameList = context.getResources().getStringArray(R.array.busan_list);
                    break;
                case Daegu:
                    mName = (provinceList[3]);
                    mLocalList = makeLocalList(context, ProvinceType.Daegu);
                    mLocalNameList = context.getResources().getStringArray(R.array.daegu_list);
                    break;
                case Incheon:
                    mName = (provinceList[4]);
                    mLocalList = makeLocalList(context, ProvinceType.Incheon);
                    mLocalNameList = context.getResources().getStringArray(R.array.incheon_list);
                    break;
                case Gwangju:
                    mName = (provinceList[5]);
                    mLocalList = makeLocalList(context, ProvinceType.Gwangju);
                    mLocalNameList = context.getResources().getStringArray(R.array.gwangju_list);
                    break;
                case Daejeon:
                    mName = (provinceList[6]);
                    mLocalList = makeLocalList(context, ProvinceType.Daejeon);
                    mLocalNameList = context.getResources().getStringArray(R.array.daejeon_list);
                    break;
                case Ulsan:
                    mName = (provinceList[7]);
                    mLocalList = makeLocalList(context, ProvinceType.Ulsan);
                    mLocalNameList = context.getResources().getStringArray(R.array.ulsan_list);
                    break;
                case SeJong:
                    mName = (provinceList[8]);
                    mLocalList = makeLocalList(context, ProvinceType.SeJong);
                    mLocalNameList = context.getResources().getStringArray(R.array.sejong_list);
                    break;
                case Gyeonggi:
                    mName = (provinceList[9]);
                    mLocalList = makeLocalList(context, ProvinceType.Gyeonggi);
                    mLocalNameList = context.getResources().getStringArray(R.array.gyeonggi_list);
                    break;
                case Gangwon:
                    mName = (provinceList[10]);
                    mLocalList = makeLocalList(context, ProvinceType.Gangwon);
                    mLocalNameList = context.getResources().getStringArray(R.array.gangwon_list);
                    break;
                case Chungcheongbuk:
                    mName = (provinceList[11]);
                    mLocalList = makeLocalList(context, ProvinceType.Chungcheongbuk);
                    mLocalNameList = context.getResources().getStringArray(R.array.chungbuk_list);
                    break;
                case Chungcheongnam:
                    mName = (provinceList[12]);
                    mLocalList = makeLocalList(context, ProvinceType.Chungcheongnam);
                    mLocalNameList = context.getResources().getStringArray(R.array.chungnam_list);
                    break;
                case Jeollabuk:
                    mName = (provinceList[13]);
                    mLocalList = makeLocalList(context, ProvinceType.Jeollabuk);
                    mLocalNameList = context.getResources().getStringArray(R.array.jeonbuk_list);
                    break;
                case Jeollanam:
                    mName = (provinceList[14]);
                    mLocalList = makeLocalList(context, ProvinceType.Jeollanam);
                    mLocalNameList = context.getResources().getStringArray(R.array.jeonnam_list);
                    break;
                case Gyeongsangbuk:
                    mName = (provinceList[15]);
                    mLocalList = makeLocalList(context, ProvinceType.Gyeongsangbuk);
                    mLocalNameList = context.getResources().getStringArray(R.array.gyeongbuk_list);
                    break;
                case Gyeongsangnam:
                    mName = (provinceList[16]);
                    mLocalList = makeLocalList(context, ProvinceType.Gyeongsangnam);
                    mLocalNameList = context.getResources().getStringArray(R.array.gyeongnam_list);
                    break;
                case Jeju:
                    mName = (provinceList[17]);
                    mLocalList = makeLocalList(context, ProvinceType.Jeju);
                    mLocalNameList = context.getResources().getStringArray(R.array.jeju_list);
                    break;

                default:
                    break;
            }
        }

        public ProvinceType getType() {
            return mType;
        }

        public String getName() {
            return mName;
        }

        public Local[] getLocalList() {
            return mLocalList;
        }

        public String[] getLocalNameList() {
            return mLocalNameList;
        }

        private Local[] makeLocalList(Context context, ProvinceType type) {
            Local[] localListSorted = null;
            String[] localNameListUnSorted = null;
            Collection<String> localNameCollection = null;
            String[] localNameListSorted = null;
            Iterator<String> iter = null;
            Local[] localListUnSorted = null;
            int iterCount = 0;

            switch (type) {
                case SouthKorea:
                    break;
                case Seoul:
                    localListSorted = new Local[numLocals[0]];
                    localNameListUnSorted = context.getResources().getStringArray(
                            R.array.seoul_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[0]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    seoulStats[0] = parsingValues(context, "Seoul.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++) {
                        seoulStats[i + 1] = parsingValues(context, "Seoul.xls", i + 1);
                    }

                    localListUnSorted = new Local[numLocals[0]];

                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("seoul_" + i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i], resID,
                                seoulStats[i]); //
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

                case Busan:
                    localListSorted = new Local[numLocals[1]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.busan_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[1]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    busanStats[0] = parsingValues(context, "Busan.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        busanStats[i + 1] = parsingValues(context, "Busan.xls", i + 1);

                    localListUnSorted = new Local[numLocals[1]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("busan_" +
                                i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID, busanStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

                case Daegu:
                    localListSorted = new Local[numLocals[2]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.daegu_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[2]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    daeguStats[0] = parsingValues(context, "Daegu.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        daeguStats[i+1] = parsingValues(context, "Daegu.xls", i + 1);

                    localListUnSorted = new Local[numLocals[2]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("daegu_" +
                                i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID,
                                daeguStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

//                case Incheon:
//                    localListSorted = new Local[numLocals[3]];
//                    localNameListUnSorted =
//                            context.getResources().getStringArray(
//                                    R.array.incheon_list);
//
//                    localNameCollection = new TreeSet<String>(
//                            Collator.getInstance());
//                    for (String name : localNameListUnSorted)
//                        localNameCollection.add(name);
//
//                    localNameListSorted = new String[numLocals[3]];
//                    iter = localNameCollection.iterator();
//                    iterCount = 0;
//                    while (iter.hasNext())
//                        localNameListSorted[iterCount++] = iter.next();
//
//                    // parsing values from excel
//                    for (int i = 0; i < localNameListUnSorted.length; i++)
//                        incheonStats[i] = parsingValues(context, "Incheon.xls", i + 1);
//
//                    localListUnSorted = new Local[numLocals[3]];
//                    for (int i = 0; i < localNameListUnSorted.length; i++) {
//                        int resID = context.getResources().getIdentifier("incheon_" +
//                                i, "drawable",
//                                context.getPackageName());
//                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
//                                resID, incheonStats[i]);
//                    }
//
//                    for (int i = 0; i < localNameListSorted.length; i++) {
//                        for (Local local : localListUnSorted) {
//                            if (localNameListSorted[i].equals(local.getName()))
//                                localListSorted[i] = local;
//                        }
//                    }
//                    break;

                case Gwangju:
                    localListSorted = new Local[numLocals[4]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.gwangju_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[4]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    gwangjuStats[0] = parsingValues(context, "Gwangju.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        gwangjuStats[i+1] = parsingValues(context, "Gwangju.xls", i + 1);

                    localListUnSorted = new Local[numLocals[4]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("gwangju_" +
                                i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID, gwangjuStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

                case Daejeon:
                    localListSorted = new Local[numLocals[5]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.daejeon_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[5]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    daejeonStats[0] = parsingValues(context, "Daejeon.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        daejeonStats[i+1] = parsingValues(context, "Daejeon.xls", i + 1);

                    localListUnSorted = new Local[numLocals[5]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("daejeon_" +
                                i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID, daejeonStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

                case Ulsan:
                    localListSorted = new Local[numLocals[6]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.ulsan_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[6]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    ulsanStats[0] = parsingValues(context, "Ulsan.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        ulsanStats[i+1] = parsingValues(context, "Ulsan.xls", i + 1);

                    localListUnSorted = new Local[numLocals[6]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("ulsan_" +
                                i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID, ulsanStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

                case SeJong:
                    localListSorted = new Local[numLocals[7]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.sejong_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[7]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    sejongStats[0] = parsingValues(context, "Sejong.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        sejongStats[i+1] = parsingValues(context, "Sejong.xls", i + 1);

                    localListUnSorted = new Local[numLocals[7]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("sejong_" +
                                i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID, sejongStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

                case Gyeonggi:
                    localListSorted = new Local[numLocals[8]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.gyeonggi_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[8]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    gyeonggiStats[0] = parsingValues(context, "Gyeonggi.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        gyeonggiStats[i+1] = parsingValues(context, "Gyeonggi.xls", i + 1);

                    localListUnSorted = new Local[numLocals[8]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("gyeonggi_"
                                + i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID, gyeonggiStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

                case Gangwon:
                    localListSorted = new Local[numLocals[9]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.gangwon_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[9]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    gangwonStats[0] = parsingValues(context, "Gangwon.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        gangwonStats[i+1] = parsingValues(context, "Gangwon.xls", i + 1);

                    localListUnSorted = new Local[numLocals[9]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("gangwon_" +
                                i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID, gangwonStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

                case Chungcheongbuk:
                    localListSorted = new Local[numLocals[10]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.chungbuk_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[10]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    chungbukStats[0] = parsingValues(context, "Chungbuk.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        chungbukStats[i+1] = parsingValues(context, "Chungbuk.xls", i + 1);

                    localListUnSorted = new Local[numLocals[10]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("chungbuk_"
                                + i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID, chungbukStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

//                case Chungcheongnam:
//                    localListSorted = new Local[numLocals[11]];
//                    localNameListUnSorted =
//                            context.getResources().getStringArray(
//                                    R.array.chungnam_list);
//
//                    localNameCollection = new TreeSet<String>(
//                            Collator.getInstance());
//                    for (String name : localNameListUnSorted)
//                        localNameCollection.add(name);
//
//                    localNameListSorted = new String[numLocals[11]];
//                    iter = localNameCollection.iterator();
//                    iterCount = 0;
//                    while (iter.hasNext())
//                        localNameListSorted[iterCount++] = iter.next();
//
//                    // parsing values from excel
//                    for (int i = 0; i < localNameListUnSorted.length; i++)
//                        chungnamStats[i] = parsingValues(context, "Chungnam.xls", i + 1);
//
//                    localListUnSorted = new Local[numLocals[11]];
//                    for (int i = 0; i < localNameListUnSorted.length; i++) {
//                        int resID = context.getResources().getIdentifier("chungnam_"
//                                + i, "drawable",
//                                context.getPackageName());
//                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
//                                resID, chungnamStats[i]);
//                    }
//
//                    for (int i = 0; i < localNameListSorted.length; i++) {
//                        for (Local local : localListUnSorted) {
//                            if (localNameListSorted[i].equals(local.getName()))
//                                localListSorted[i] = local;
//                        }
//                    }
//                    break;

                case Jeollabuk:
                    localListSorted = new Local[numLocals[12]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.jeonbuk_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[12]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    jeonbukStats[0] = parsingValues(context, "Jeonbuk.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        jeonbukStats[i+1] = parsingValues(context, "Jeonbuk.xls", i + 1);

                    localListUnSorted = new Local[numLocals[12]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("jeonbuk_" +
                                i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID, jeonbukStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

                case Jeollanam:
                    localListSorted = new Local[numLocals[13]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.jeonnam_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[13]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    jeonnamStats[0] = parsingValues(context, "Jeonnam.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        jeonnamStats[i+1] = parsingValues(context, "Jeonnam.xls", i + 1);

                    localListUnSorted = new Local[numLocals[13]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("jeonnam_" +
                                i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID, jeonnamStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

                case Gyeongsangbuk:
                    localListSorted = new Local[numLocals[14]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.gyeongbuk_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[14]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    gyeongbukStats[0] = parsingValues(context, "Gyeongbuk.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        gyeongbukStats[i+1] = parsingValues(context, "Gyeongbuk.xls", i + 1);

                    localListUnSorted = new Local[numLocals[14]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("gyeongbuk_"
                                + i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID, gyeongbukStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

                case Gyeongsangnam:
                    localListSorted = new Local[numLocals[15]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.gyeongnam_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[15]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    gyeongnamStats[0] = parsingValues(context, "Gyeongnam.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        gyeongnamStats[i+1] = parsingValues(context, "Gyeongnam.xls", i + 1);

                    localListUnSorted = new Local[numLocals[15]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("gyeongnam_"
                                + i, "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID, gyeongnamStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

                case Jeju:
                    localListSorted = new Local[numLocals[16]];
                    localNameListUnSorted =
                            context.getResources().getStringArray(
                                    R.array.jeju_list);

                    localNameCollection = new TreeSet<String>(
                            Collator.getInstance());
                    for (int i = 1; i < localNameListUnSorted.length; i++) {
                        String name = localNameListUnSorted[i];
                        localNameCollection.add(name);
                    }
                    localNameListSorted = new String[numLocals[16]];
                    iter = localNameCollection.iterator();
                    iterCount = 1;
                    localNameListSorted[0] = localNameListUnSorted[0];
                    while (iter.hasNext())
                        localNameListSorted[iterCount++] = iter.next();

                    // parsing values from excel
                    jejuStats[0] = parsingValues(context, "Jeju.xls",
                            localNameListUnSorted.length);
                    for (int i = 0; i < localNameListUnSorted.length - 1; i++)
                        jejuStats[i+1] = parsingValues(context, "Jeju.xls", i + 1);

                    localListUnSorted = new Local[numLocals[16]];
                    for (int i = 0; i < localNameListUnSorted.length; i++) {
                        int resID = context.getResources().getIdentifier("jeju_" + i,
                                "drawable",
                                context.getPackageName());
                        localListUnSorted[i] = new Local(localNameListUnSorted[i],
                                resID, jejuStats[i]);
                    }

                    for (int i = 0; i < localNameListSorted.length; i++) {
                        for (Local local : localListUnSorted) {
                            if (localNameListSorted[i].equals(local.getName()))
                                localListSorted[i] = local;
                        }
                    }
                    break;

                default:
                    break;
            }

            return localListSorted;
        }
    }

    public static class Local {
        private String mName = null;
        private int mDrawableId = -1;
        private int[] mStatistics = {
                50, 50, 50, 50, 50
        };

        public Local(String name, int id, int[] stats) {
            mName = name;
            mDrawableId = id;
            mStatistics = stats;
        }

        public String getName() {
            return mName;
        }

        public int getDrawableId() {
            return mDrawableId;
        }

        public int getCount(int i) {
            return mStatistics[i];
        }
    }
}
