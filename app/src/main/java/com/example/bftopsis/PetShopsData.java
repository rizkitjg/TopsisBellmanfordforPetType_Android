package com.example.bftopsis;

import java.util.ArrayList;

public class PetShopsData {
    public static String[][] data = new String[][]{
            {"Pet Planet", "Jl. Kapten Patimura No.360 / 98, Darat, Kec. Medan Baru, Kota Medan, Sumatera Utara 20153","https://lh5.googleusercontent.com/p/AF1QipPt8EJ3Ss67Ws1QbnxtiHDHxkqXZy9KCxR5wzuS=s644-k-no"},
            {"Alona Petshop", "Jl. Sei Batang Serangan No.35/58, Babura, Kec. Medan Baru, Kota Medan, Sumatera Utara 20222", "https://lh5.googleusercontent.com/p/AF1QipOYznwaD5qJyEJ140D_ELkSMpSrTyLZXRZN35Vh=w408-h544-k-no"},
            {"Brastagi Petshop", "Jl. Sei Padang No.98, Merdeka, Kec. Medan Baru, Kota Medan, Sumatera Utara 20154", "https://lh5.googleusercontent.com/p/AF1QipPVAUkjFtpnrhL5cm7ZdIgVzJuFNL2l4M0kXYcT=w408-h725-k-no"},
            {"QUEEN PETSHOP", "Jl. Pembangunan, Padang Bulan, Kec. Medan Baru, Kota Medan, Sumatera Utara 20155", "https://lh5.googleusercontent.com/p/AF1QipOJ0dlnkrQetfkj5qZs0myI_uyEJL2EvCeJu-xQ=w408-h306-k-no"},
            {"Akbar Petshop", "Jl. Setia Budi No.190, Tj. Rejo, Kec. Medan Sunggal, Kota Medan, Sumatera Utara 20122", "https://lh5.googleusercontent.com/p/AF1QipOQEcT98sWWwRaWKwyA7CNkgBRkdoMkNxm2raKc=w426-h240-k-no"},
            {"Buana Vet", "Jl. Abdullah Lubis No.9, Darat, Kec. Medan Baru, Kota Medan, Sumatera Utara 20152", "https://lh5.googleusercontent.com/p/AF1QipNOdcrfbedpKe77A5zhuKJG0mXnTt4sL8RW9n2W=w408-h306-k-no"},
            {"Hobby Pet (Petshop and care)", "Jl. Darussalam No.123, Babura Sunggal, Kec. Medan Baru, Kota Medan, Sumatera Utara 20122", "https://lh5.googleusercontent.com/p/AF1QipPhL7fCw_DcGf9q4hNDkK61_ZrKz8bNBg10jbpA=w408-h725-k-no"},
            {"Mickey Pet Shop", "Jl. Setia Budi, Tj. Sari, Kec. Medan Selayang, Kota Medan, Sumatera Utara 20154", "https://lh5.googleusercontent.com/p/AF1QipOeQDOfsowT9nO5PN0wbHJsLyq4OA0EcE7pL-lm=w408-h544-k-no"},
            {"Darussalam Petshop", "Jl. Darussalam No.109, Babura, Kec. Medan Baru, Kota Medan, Sumatera Utara 20154", "https://lh5.googleusercontent.com/p/AF1QipN3lLoM7S-JYRYOZH8O0NnSgviXb1EonRI9Bgrv=w408-h544-k-no"},
            {"Gober Petshop", "HMR9+PJF, Pandau Hilir, Kec. Medan Perjuangan, Kota Medan, Sumatera Utara 20233", "https://streetviewpixels-pa.googleapis.com/v1/thumbnail?panoid=FrR-HGoNiUCxyZozAZNoyg&cb_client=search.gws-prod.gps&w=408&h=240&yaw=103.311&pitch=0&thumbfov=100"},
            {"Molys Petshop", "Jl. S. Parman, Petisah Tengah, Kec. Medan Petisah, Kota Medan, Sumatera Utara 20111", "https://lh5.googleusercontent.com/p/AF1QipOHctLbneEG9haLcCWSqfNj6tFRlJWc923ljzQt=w408-h725-k-no"},
            {"Pussy petshop", "Simpang laksana, Jl.Laksana, simpang Jl. Halat No.99, laksana, Kota Medan, Sumatera Utara", "https://lh4.googleusercontent.com/proxy/UpOV5fHAJTXzJ_QdzaFf4DmRRkoW4GlEaGd6PfvCISjs5C6DAu6kYnNy2TOrgC09ujs-Ha8-Rqnebz8uv0idgpCfvfVRMNELCt5Urw1Duty5r76TbQ3nNjYraMsdsfmAqCfEuTHjcA60Q0IqIh-t11UxHdHLFVt0oneEa4N3dDUP=w408-h306-k-no"},
            {"Brastagi pet shop halat", "Jl. Halat No.52, Ps. Merah Bar., Kec. Medan Kota, Kota Medan, Sumatera Utara 20214", "https://lh5.googleusercontent.com/p/AF1QipPtgxKp0yBuQhhxl6kf6JNGZsgp-f5Kw8nVQq42=w408-h544-k-no"},
            {"Kj petshop", "Kj petshop, Jl. Halat No.72, Matsum II City, Medan Area, Medan City, North Sumatra 20216", "https://lh5.googleusercontent.com/p/AF1QipPhCtwTXI56fHJhWpsawTLFYtNFdr5ssXYn48Oe=w426-h240-k-no"},
            {"Happy PETS serdang medan", "Jl. Prof. HM. Yamin Sh No.30, Gg. Buntu, Kec. Medan Tim., Kota Medan, Sumatera Utara 20232", "https://lh5.googleusercontent.com/p/AF1QipOOeLuedCh5t9EFTckzH1fLqTl17KiIrrsGzZTb=w408-h725-k-no"},
            {"Bonavista Pet Shop", "Jl. M.H Thamrin No.73A, Pandau Hulu I, Kec. Medan Kota, Kota Medan, Sumatera Utara 20211", "https://lh4.googleusercontent.com/proxy/J0el7kwhIXoFZMqpm3R6d2orrisUrFo-sr0SkU4-wNKs4tAqeNDbhbn-bnitJqHvydWUM_N63jTx5zlfPPHnyIjqeYNHJ605j5-7-6dPnrv4iAkjDrZR2-ECVnj3E6XSw2ZYHFaOCeax9pwowFgWNsb7kn653dRksREBYc8onsjZ=w408-h306-k-no"},
            {"Pluto Petshop", "Jl. Aipda KS Tubun, Pandau Hulu I, Kec. Medan Kota, Kota Medan, Sumatera Utara 20211", "https://lh5.googleusercontent.com/p/AF1QipOhj6vC5rAchhr7w4CdVX2Py-dN7Fx9BqAKol3K=w408-h544-k-no"},
            {"Boobo Petshop", "Jl. HOS Cokroaminoto No.89 A, Pandau Hilir, Kec. Medan Perjuangan, Kota Medan, Sumatera Utara 20233", "https://lh5.googleusercontent.com/p/AF1QipNk1vIuJBuHbxfwA7E8-oy1bOa7phnEh0fbT2u6=w408-h725-k-no"},
            {"Erna Pet Shop", "20233, Sidodadi, East Medan, Medan City, North Sumatra 20233", "https://lh5.googleusercontent.com/p/AF1QipOdOhehEXiuAdfa3YF0R8i0NOcDEf9-6TMV5iop=w426-h240-k-no"},
            {"Venice Petshop", "Jl. Prof. HM. Yamin Sh No.147C, Sei Kera Hilir II, Kec. Medan Perjuangan, Kota Medan, Sumatera Utara 20232", "https://lh5.googleusercontent.com/p/AF1QipP77wHSabcBTureGlUDn2FOIgzG2sFjU87LenGT=w408-h408-k-no"},
    };

    public static ArrayList<PetShop> getListData() {
        ArrayList<PetShop> list = new ArrayList<>();
        for(String[] aData : data) {
            PetShop petshop = new PetShop();
            petshop.setName(aData[0]);
            petshop.setAddress(aData[1]);
            petshop.setPhoto(aData[2]);
            list.add(petshop);
        }
        return list;
    }
}
