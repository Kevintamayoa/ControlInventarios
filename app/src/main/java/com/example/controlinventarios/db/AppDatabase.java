package com.example.controlinventarios.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.controlinventarios.Dao.AssembliesDao;
import com.example.controlinventarios.Dao.AssemblyProductsDao;
import com.example.controlinventarios.Dao.ProductCategoriesDao;
import com.example.controlinventarios.Dao.ProductsDao;

@Database(entities = {Assemblies.class,AssemblyProducts.class,Customers.class,
        OrderAssemblies.class,Orders.class,OrderStatus.class,ProductCategories.class,Products.class}, version = 1) //, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE = null;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,
                    AppDatabase.class, "inventory.db")
                    .allowMainThreadQueries()
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            db.execSQL("INSERT INTO product_categories (id, description) VALUES (0, 'Disco duro')");
                            db.execSQL("INSERT INTO product_categories (id, description) VALUES (1, 'Memoria')");
                            db.execSQL("INSERT INTO product_categories (id, description) VALUES (2, 'Monitor')");
                            db.execSQL("INSERT INTO product_categories (id, description) VALUES (3, 'Procesador')");
                            db.execSQL("INSERT INTO product_categories (id, description) VALUES (4, 'Tarjeta madre')");
                            db.execSQL("INSERT INTO product_categories (id, description) VALUES (5, 'Tarjeta de video')");
                            db.execSQL("INSERT INTO product_categories (id, description) VALUES (6, 'Tarjeta de sonido')");

                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (0, 0, 'Western Digital Purple WD10PURX, 3.5\", 1TB, SATA3, 6GB/s, 64MB, IntelliPower', 149900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (1, 0, 'Western Digital 3.5\" SATA de 320GB a 7200 RPM / New-Pulls / WD3200AAJS / WD3200AAKS', 43500, 4)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (2, 0, 'Toshiba 3.5\" 3TB, SATA 6.0Gb/s 64MB Cache, 7200 RPM', 209900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (3, 0, 'Toshiba 3.5\" de 1TB SATA', 1099, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (4, 0, 'Seagate ST2000DM006 2TB 3.5\" 7200RPM SATA3 64MB', 158900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (5, 0, 'Seagate ST3000DM001 3TB 3.5\" 7200RPM SATA3 64MB', 209900, 5)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (6, 0, 'Western Digital Purple WD20PURX, 3.5\", 2TB, SATA3, 6GB/s, 64MB, IntelliPower', 209900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (7, 0, 'Toshiba 3.5\" 2TB, SATA 6.0Gb/s 64MB Cache, 7200 RPM', 15600, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (8, 0, 'Seagate 3.5\" SATA de 4TB, 6 Gb/s, 5900 RPM', 289900, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (9, 0, 'Toshiba 3.5\" (DT01ACA050) SATA III / 500GB -- 7200RPM / 32MB', 104900, 5)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (10, 0, 'Seagate 3.5\" 1TB Sata III 7200 RPM 64 MB ST1000DM003', 109900, 7)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (11, 0, 'Seagate 500GB 7200 Rpm SATA 6GB/s (ST500DM002) 16 Mb', 99900, 4)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (12, 0, 'Estado sólido Kingston UV400 de 480 GB, 2.5\" SATA III (6GB/s)', 298899, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (13, 0, 'Estado sólido Kingston UV400 de 120 GB, 2.5\" SATA III (6GB/s)', 109900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (14, 0, 'Estado Sólido Kingston 480GB SSDNow V300 SATA III 2.5'' + Adaptador (sin kit)', 319900, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (15, 0, 'Unidad SSD Kingston 120GB SATA III 2.5\" V300 (no incluye kit)', 99900, 6)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (16, 0, 'Estado sólido Kingston UV400 de 240 GB, 2.5\" SATA III (6GB/s)', 179900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (17, 0, 'Estado Sólido Kingston 480GB SATA 3 Serie UV300 A/7mm', 269900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (18, 0, 'UNIDAD SSD KINGSTON 240GB SATA III 2.5\" V300 SV300S37A/240G (no incluye kit)', 149900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (100, 1, 'Adata DDR PC3200, 400MHz, 1GB, CL2.5', 58500, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (101, 1, 'Patriot Signature DDR3, PC3-12800 (1600MHz), 2 GB', 33500, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (102, 1, 'Corsair Value Select DDR3L, 1600MHz, 4GB, CL11', 49900, 4)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (103, 1, 'Corsair XMS3 DDR3 1600 PC3-12800 8GB CL11', 109900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (104, 1, 'Corsair Vengeance 8GB DDR3 1600 MHz', 113900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (105, 1, 'Corsair Dominator Platinum 8GB (2 x 4GB) 1600MHz, PC3-12800', 219900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (106, 1, 'Corsair DDR3 a 1600 MHz de 4GB PC3-12800 Vengeance Color Negro', 59900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (107, 1, 'Corsair DDR3 a 1333 MHz de 8GB PC3-10600', 112900, 5)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (108, 1, 'Corsair DDR3 a 1333 MHz de 4GB PC3-10600 XMS', 57500, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (109, 1, 'Kingston DDR3 1333MHz de 8GB PC-10600', 86900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (110, 1, 'Corsair Value Select DDR3 PC3-12800 (1600MHz), CL11, 8 GB', 99900, 6)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (111, 1, 'Corsair Vengeance DDR3, PC3-12800, 16GB (2x8GB), 1600 MHz, Dual Channel', 239900, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (112, 1, 'Corsair XMS3 4GB DDR3 a 1600 MHz, DIMM', 66500, 4)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (113, 1, 'Corsair DDR a 1600 MHz de 8GB PC3-12800 Vengeance', 119900, 6)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (114, 1, 'Corsair DDR3 a 1600 MHz de 4GB PC3-12800 Vengeance', 59900, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (115, 1, 'Corsair DDR3 a 1333 MHz de 4GB PC3-10600', 57500, 7)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (116, 1, 'Corsair Vengeance LPX DDR4 PC4 19200 (2400MHz), CL16, 8 GB', 114900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (117, 1, 'Corsair Vengeance DDR4, PC4-19200 (2400MHz), CL14, 16 GB, kit (2 x 8 GB)', 229900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (118, 1, 'DDR4 CORSAIR 4GB ValueSelect 2133MHz 1.2V C15 CMV4GX4M1A2133C15', 59900, 4)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (119, 1, 'Corsair Vengeance DDR4 PC4 19200 (2400MHz), CL16, 4 GB', 64900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (120, 1, 'Corsair Value Select DDR4, PC4-17000 (2133 MHz), CL15, 8 GB', 109900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (200, 2, 'LCD ACER 17\" V173 DJOb 1280x1024 20000:1 5MS Color Negro (Cuadrado)', 129900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (201, 2, 'Touchscreen Elo 1509L IntelliTouch, 15\", Gris', 599900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (202, 2, 'HP Passport 1912nm para Internet de 18.5\"', 329901, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (203, 2, 'LED Samsung LS24F350FHLXZX de 23.6\", Resolución 1920 x 1080 (Full HD), 4 ms, HDMI, VGA', 279900, 10)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (204, 2, 'LED Samsung S19F350HNL de 18.5\", Resolución 1366 x 768, 14 ms', 139899, 4)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (205, 2, 'LED HP 19KA, 18.5\", resolución 1366x768, VGA, Negro', 139899, 6)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (206, 2, 'LED HP V193 de 18.5\", resolución 1366 x 768, VGA, incluye teclado y mouse HP', 169901, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (207, 2, 'LED LG 22M38A-B de 21.5\", resolución 1920 x 1080 Full HD, VGA', 199900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (208, 2, 'LG 19M38A-B, 18.5\" LED, 1366 x 768 ,5ms, VGA, Negro', 149900, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (209, 2, 'LED LG 19M37A de 18.5\", Resolución 1366 x 768', 149900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (210, 2, 'HP ProDisplay P201 de 20 pulgadas con retroiluminación LED', 219900, 5)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (211, 2, 'LED Acer P166HQL Bb, 15\", WideScreen 1366x768, Negro', 119900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (212, 2, 'LED Samsung S27F350FHL de 27\", Resolución 1920 x 1080 (Full HD), 4 ms, HDMI, VGA', 459901, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (213, 2, 'LED Samsung LS22F350FHLXZX de 21.5\", Resolución 1920 x 1080 (Full HD), 5 ms, HDMI, VGA', 219900, 4)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (214, 2, 'LED DELL E1916H de 19\", resolución 1366 x 768 (210-AGND)', 159900, 7)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (215, 2, 'LED LG 24M38H-B de 23.6\", Resolución 1920 x 1080 Full HD, HDMI, VGA', 249900, 8)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (216, 2, 'LG 20MP48A-P, 19.5\", 1440x900, 5ms, VGA, IPS LED, Negro', 159900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (217, 2, 'LG 27MP38VQ-B, 27\" LED, 1920 x 1080, 5ms, DVI-D, VGA, HDMI', 409900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (218, 2, 'Thunderbolt Display (27 pulgadas)', 1899900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (219, 2, 'ACER 19.5\" LED V206HQL, VGA, VESA, Color Negro', 149900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (220, 2, 'LED Acer V176L B-MX 17\", 1280 x 1024', 149900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (300, 3, 'AMD A-Series A10 7870KBE 4.1 GHz 95W SOC FM2+ (AD787KXDJCSBX)', 279900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (301, 3, 'AMD A-Series A10 7890KBE 4.3 GHz 95W SOC FM2+ (AD789KXDJCHBX)', 326900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (302, 3, '(APU) AMD A8-7650K a 3.3 GHz con Gráficos Radeon R7, Caché 4MB, Socket FM2+, Quad-Core', 179900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (303, 3, 'AMD APU A10-7700K, 3.4GHZ, Socket FM2+, AMD Radeon R7', 241899, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (304, 3, 'AMD A-Series A4 4000 a 3.2GHz, 65W, 1MB Caché, Socket FM2', 64900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (305, 3, 'AMD A6 5400K 3.6GHZ Socket FM2 Caja.', 99900, 10)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (306, 3, 'AMD A-Series A10 7860KBE 4.0 GHz 95W SOC FM2+ (AD786KYBJCSBX)', 219900, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (307, 3, '(APU) AMD A6 7400K a 3.5 GHz con Gráficos Radeon R5, Socket FM2+, Dual-Core, 65W', 116900, 5)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (308, 3, '(APU) AMD A8-7600 a 3.1 GHz con Gráficos Radeon R7, Caché 4MB, Socket FM2+, Quad-Core', 159900, 6)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (309, 3, 'AMD (AD6300OKHLBOX) A4-6300, Radeon HD 8370D, Soc FM2.', 69500, 5)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (310, 3, 'AMD A6 6400K BE 4.1GHZ Socket FM2 Caja.', 103900, 10)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (311, 3, 'Intel Core i3-6100, 6ta Gen, 3.7 GHz, Intel HD Graphics 530, Socket 1151, L3 Caché 3 MB', 259900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (312, 3, 'Intel Pentium Dual Core G3240, 3.1 Ghz, 3M Cache', 112900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (313, 3, 'Intel Core i5 4590 3.3GHz, 6MB, 84w, Socket 1150 (BX80646I54590)', 418900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (314, 3, 'Intel BX80646I54460, Core I5-4460, 3.4 GHz, 6MB, 84W, 22nm, Socket 1150', 399900, 4)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (315, 3, 'Intel Core i3 4150 4ta Gen, 3.5 GHz, 3MB Caché, Socket 1150', 249900, 7)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (316, 3, 'Intel Core i5-4690 4ta Generación a 3.5 GHz, 6MB Caché, Socket 1150', 489900, 5)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (317, 3, 'Intel Core I3 3220 3.3Ghz 3MB Socket Lga 1155', 231900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (318, 3, 'Intel Core i5-6400 de 6ta Gen, 2.7 GHz, Intel HD Graphics 530, Socket 1151, L3 Caché 6 MB', 399900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (319, 3, 'Intel Celeron G1840 a 2.80 GHz, Intel HD Graphics, Soc1150, Caché 2MB, Dual-Core, 22nm', 79900, 10)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (320, 3, 'Intel Corei3-4160 3.6GHz, 3MB Cache, 22nm, 54w, Socket 1150 (BX80646I34160)', 239900, 11)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (321, 3, 'Intel Pentium Dual Core G3250, 3.20 Ghz, 3M Cache', 119900, 8)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (322, 3, 'Intel Core i7-4790 a 3.6 GHz, 8MB Caché, 84W, 22Nm, Socket 1150', 659900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (323, 3, 'Intel Core i7-3820 3.6GHz 5GT/S 10MB 130w Soc 2011', 628900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (324, 3, 'INTEL COREI3 540 3.06GHZ DDR3 1333/1066 4MB L3 TDP95W SOC1156 CAJA', 178900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (400, 4, 'ECS A68F2P-M4, 2xDDR3, PCIe, VGA/HDMI, Socket FM2+', 95500, 4)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (401, 4, 'Gigabyte F2A88XM-D3HP, Socket FM2+, 4xDDR3, PCI-E 2 / 3 , SATA, ATX, VGA,DVI, GLAN', 152900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (402, 4, 'Gigabyte GA-F2A68HM-DS2H, 2xDDR3, VGA, GLAN, Socket FM2+', 114900, 6)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (403, 4, 'ECS A55F-M3 V.A 2xDDR3 3xP-cie 2.0 SATA 3 Socket FM1', 101900, 7)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (404, 4, 'Gigabyte GA-G1.SNIPER A88X, Socket FM2, VGA, DVI, HDMI, Son 8 Ch, GLAN', 209900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (405, 4, 'Gigabyte GA-F2A68HM-H, 2xDDR3, PCIe, HDMI, Socket FM2+', 108900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (406, 4, 'ECS A55F-M4 (2.0) 2xDDR3 2PCIe, VGA, Socket FM1', 90500, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (407, 4, 'Gigabyte G1.Sniper B7, 4xDDR4, PCIe, Gigabit, DVI / HDMI, Socket 1151', 229900, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (408, 4, 'Gigabyte B150M-D3H-GSM, 4xDDR4, PCIe, Gigabit, HDMI / DVI/ VGA, Socket 1151', 179900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (409, 4, 'Gigabyte GA-B85M-D3V, 2xDDR3, DVI/VGA, PCIe, GigaLAN, Socket 1150', 123900, 4)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (410, 4, 'Gigabyte GA-H81M-DS2, 2xDDR3, PCIE, VGA, GLAN, Paralelo, Socket 1150', 11500, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (411, 4, 'ECS (H81H3-M4) 2DDR3, 2PCIe Gigalan, USB3, HDMI, Soc1150', 94500, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (412, 4, 'Gigabyte A-H110M-S2, 2xDDR4, PICe, Gigabit, VGA, Socket 1151', 116900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (413, 4, 'Gigabyte GA-B85M-DS3H-A, 4xDDR3, VGA, HDMI, DVI, Gigabit, Socket 1150', 149900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (414, 4, 'Asrock micro ATX H81M-VG4, 2xDDR3, VGA, GigaLAN, PCIe, Socket 1150', 93499, 6)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (415, 4, 'Gigabyte GA-B85M-DS3H Intel B85, 4xDDR3, PCIE,Socket 1150', 143900, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (500, 5, 'NVIDIA Gigabyte GeForce GT 420, 2GB GDDR3, 1xHDMI, 1xDVI, 1xVGA, PCI Express x16 2.', 112900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (501, 5, 'Gigabyte GV-N210SL-1GI, GeForce 210, 1GB DDR3, 64Bit, HDMI/DVI/VGA, PCIE 2.0', 73500, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (502, 5, 'Gigabyte Radeon R7 360, 2 GB GDDR5, Display Port, HDMI, DVI, Puerto PCI Express 3.0', 249900, 11)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (503, 5, 'Gigabyte NVIDIA GeForce GTX 750 Ti OC, 2 GB GDDR5, HDMI, DVI, Puerto PCI Exp 3.0', 279900, 7)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (504, 5, 'Gigabyte GV-N75TWF2OC-4GI, GTX 750TI, 4GB DDR5, 128Bit, PCI-E 3.0,DVI, HDMI', 349900, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (505, 5, 'Gigabyte GV-N740D50C-2GI GT 740 2GB (DDR5), PCIe 3.0, VGA, 2DVI, HDMI', 229900, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (506, 5, 'ASUS (R7250-1GD5) 1GB DDR5, 128Bit, PCIE 3.0.', 229900, 9)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (507, 5, 'AMD Gigabyte Radeon RX 460, 4GB GDDR5, HDMI, DVI, DP, PCI Express x16 3.0', 326900, 1)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (508, 5, 'Gigabyte NVIDIA GeForce GT 710, 2GB 64-bit DDR3, PCI Express 2.0 x8', 97500, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (509, 5, 'Gigabyte AMD Radeon R5 230, 1GB 64-bit DDR3, PCI Express 2.1', 77500, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (510, 5, 'Gigabyte GV-R724OC-2GI AMD Radeon R7 240 OC, 2GB 128-bit DDR3, PCI Express 3.0', 152900, 0)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (511, 5, 'Sapphire R7250 1GB GDDR5 128Bit PCIE, VGA / DVI / HDMI (11215-00-20G)', 191900, 5)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (512, 5, 'Sapphire 11215-05-20G R7250 1GB GDDR5 128Bit PCIe', 253900, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (513, 5, 'XFX ONXFX1PLS2 HD 5450 1GB DDR3 64Bit PCI-E 2.1', 68500, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (600, 6, 'Tarjeta de Sonido Cablink, USB 2.0 a 5.1 Convertidor 3D Sound', 10500, 2)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (601, 6, 'Adaptador de Audio Manhattan 3-D USB de Alta Velocidad', 15900, 3)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (602, 6, 'Sound Blaster Sabrent 3-D 8 Channel Usb 2.0 Ext 7.1 USB-SND8', 49900, 6)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (603, 6, 'Adaptador de Tarjeta de Sonido Externa USB a Audio Estéreo 7,1 Virtual', 42500, 4)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (604, 6, 'Adaptador de Tarjeta de Sonido Externa USB a Audio Estéreo 7,1 Virtual', 42500, 4)");
                            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (605, 6, 'Adaptador de sonido USB 2.0 externo / USB-SBCV', 14900, 0)");

                            db.execSQL("INSERT INTO assemblies (id, description) VALUES (0, 'Basic #1')");
                            db.execSQL("INSERT INTO assemblies (id, description) VALUES (1, 'Basic #2')");
                            db.execSQL("INSERT INTO assemblies (id, description) VALUES (2, 'Advanced #1')");
                            db.execSQL("INSERT INTO assemblies (id, description) VALUES (3, 'Advanced #2')");
                            db.execSQL("INSERT INTO assemblies (id, description) VALUES (4, 'Professional #1')");
                            db.execSQL("INSERT INTO assemblies (id, description) VALUES (5, 'Professional #2')");
                            db.execSQL("INSERT INTO assemblies (id, description) VALUES (6, 'Gamer #1')");
                            db.execSQL("INSERT INTO assemblies (id, description) VALUES (7, 'Gamer #2')");

                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (1, 0, 1, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (2, 0, 101, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (3, 0, 205, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (4, 0, 304, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (5, 0, 400, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (6, 0, 509, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (7, 1, 9, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (8, 1, 102, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (9, 1, 206, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (10, 1, 312, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (11, 1, 414, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (12, 1, 508, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (13, 2, 10, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (14, 2, 109, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (15, 2, 207, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (16, 2, 300, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (17, 2, 500, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (18, 2, 601, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (19, 3, 4, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (20, 3, 110, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (21, 3, 207, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (22, 3, 317, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (23, 3, 415, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (24, 3, 510, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (25, 3, 601, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (26, 4, 2, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (27, 4, 119, 2)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (28, 4, 213, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (29, 4, 316, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (30, 4, 409, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (31, 4, 506, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (32, 4, 602, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (33, 5, 8, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (34, 5, 120, 2)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (35, 5, 212, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (36, 5, 318, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (37, 5, 408, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (38, 5, 505, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (39, 5, 602, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (40, 6, 8, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (41, 6, 13, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (42, 6, 116, 2)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (43, 6, 217, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (44, 6, 323, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (45, 6, 407, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (46, 6, 507, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (47, 6, 605, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (48, 7, 8, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (49, 7, 12, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (50, 7, 117, 4)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (51, 7, 218, 2)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (52, 7, 322, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (53, 7, 407, 1)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (54, 7, 504, 2)");
                            db.execSQL("INSERT INTO assembly_products (aux, id, product_id, qty) VALUES (55, 7, 605, 1)");

                            db.execSQL("INSERT INTO customers (id, first_name, last_name, address, phone1, phone2, phone3, e_mail) VALUES (0, 'Manuel', 'Vázquez', 'C.59A x 90 y 94, Fracc. Los Almendros', '998-3568541', NULL, NULL, 'manuelvz@outlook.com')");
                            db.execSQL("INSERT INTO customers (id, first_name, last_name, address, phone1, phone2, phone3, e_mail) VALUES (1, 'José', 'Medina', 'C.42 x 51 y 53, Col. Centro', '997-5491235', '997-4561289', NULL, 'medina96@yahoo.com')");
                            db.execSQL("INSERT INTO customers (id, first_name, last_name, address, phone1, phone2, phone3, e_mail) VALUES (2, 'Erika', 'Mojica', 'C.18 x Av. Sierra, Fracc. La Huerta', '952-4567823', '998-6324582', '998-6541279', 'erikam98@gmail.com')");
                            db.execSQL("INSERT INTO customers (id, first_name, last_name, address, phone1, phone2, phone3, e_mail) VALUES (3, 'Gabriel', 'Alemán', 'Av. Justo Madero x 44, Col. Boulevard', NULL, NULL, NULL, 'gabasoluciones@live.com')");
                            db.execSQL("INSERT INTO customers (id, first_name, last_name, address, phone1, phone2, phone3, e_mail) VALUES (4, 'Perla', 'Montalvo', 'C.101B x 98 y 100, Fracc. Vista Alegre', '977-4612536', '995-1268745', NULL, NULL)");
                            db.execSQL("INSERT INTO customers (id, first_name, last_name, address, phone1, phone2, phone3, e_mail) VALUES (5, 'Alfredo', 'Huerta', 'C.23 x 32 y 36A, Col. Cantaritos', NULL, NULL, NULL, 'huerta78@alpha.com.mx')");

                            //-- Pendiente: es cuando se introduce al sistema, puede ser confirmado o cancelado por el cliente
                            //        -- Cancelado: no procedió el pedido
                            //-- Confirmado: el cliente pagó el pedido, ya puede ser procesado y puesto en tránsito
                            //        -- En tránsito: pedido enviado al cliente, en espera de confirmación de recibido
                            //        -- Finalizado: estado final de un pedido entregado exitosamente
                            db.execSQL("INSERT INTO order_status (id, description, editable, previous, next) VALUES (0, 'Pendiente', 1, '-', '1,2')");
                            db.execSQL("INSERT INTO order_status (id, description, editable, previous, next) VALUES (1, 'Cancelado', 0, '0', '-')");
                            db.execSQL("INSERT INTO order_status (id, description, editable, previous, next) VALUES (2, 'Confirmado', 0, '-', '3')");
                            db.execSQL("INSERT INTO order_status (id, description, editable, previous, next) VALUES (3, 'En tránsito', 0, '-', '4')");
                            db.execSQL("INSERT INTO order_status (id, description, editable, previous, next) VALUES (4, 'Finalizado', 0, '-', '-')");

                            db.execSQL("INSERT INTO orders (id, status_id, customer_id, date, change_log) VALUES (0, 4, 5, '05-10-2016', NULL)");
                            db.execSQL("INSERT INTO orders (id, status_id, customer_id, date, change_log) VALUES (1, 4, 2, '12-11-2016', NULL)");
                            db.execSQL("INSERT INTO orders (id, status_id, customer_id, date, change_log) VALUES (2, 4, 2, '26-12-2016', NULL)");
                            db.execSQL("INSERT INTO orders (id, status_id, customer_id, date, change_log) VALUES (3, 3, 4, '03-01-2017', NULL)");
                            db.execSQL("INSERT INTO orders (id, status_id, customer_id, date, change_log) VALUES (4, 1, 1, '15-01-2017', NULL)");
                            db.execSQL("INSERT INTO orders (id, status_id, customer_id, date, change_log) VALUES (5, 3, 0, '04-02-2017', NULL)");
                            db.execSQL("INSERT INTO orders (id, status_id, customer_id, date, change_log) VALUES (6, 2, 1, '05-03-2017', NULL)");
                            db.execSQL("INSERT INTO orders (id, status_id, customer_id, date, change_log) VALUES (7, 0, 4, '12-03-2017', NULL)");
                            db.execSQL("INSERT INTO orders (id, status_id, customer_id, date, change_log) VALUES (8, 0, 3, '18-03-2017', NULL)");

                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (1, 0, 0, 2)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (2, 0, 3, 2)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (3, 0, 5, 3)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (4, 1, 1, 2)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (5, 1, 4, 2)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (6, 1, 6, 3)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (7, 1, 7, 4)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (8, 2, 2, 1)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (9, 2, 3, 1)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (10, 3, 2, 4)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (11, 3, 4, 4)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (12, 3, 5, 2)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (13, 3, 7, 2)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (14, 4, 0, 2)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (15, 4, 3, 4)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (16, 4, 4, 4)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (17, 4, 7, 2)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (18, 5, 1, 1)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (19, 5, 2, 3)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (20, 5, 5, 1)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (21, 6, 0, 3)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (22, 6, 1, 3)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (23, 6, 3, 2)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (24, 6, 4, 2)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (25, 6, 6, 4)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (26, 7, 1, 3)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (27, 7, 3, 3)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (28, 8, 0, 1)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (29, 8, 6, 5)");
                            db.execSQL("INSERT INTO order_assemblies (aux, id, assembly_id, qty) VALUES (30, 8, 7, 3)");

                        }
                    })
                    .build();
        }

        return INSTANCE;
    }

    public abstract AssembliesDao assembliesDao();
    public abstract AssemblyProductsDao assemblyProductsDao();
    public abstract ProductsDao productsDao();
    public abstract ProductCategoriesDao productCategoriesDao();

}
