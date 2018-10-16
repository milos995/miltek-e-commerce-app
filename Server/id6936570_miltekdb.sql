-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Sep 20, 2018 at 10:55 AM
-- Server version: 10.2.12-MariaDB
-- PHP Version: 7.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id6936570_miltekdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `brand`
--

CREATE TABLE `brand` (
  `brand_id` int(11) NOT NULL,
  `brand` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `brand`
--

INSERT INTO `brand` (`brand_id`, `brand`) VALUES
(1, 'Acer'),
(2, 'Asus'),
(3, 'Apple'),
(4, 'Lenovo'),
(5, 'Dell'),
(6, 'HP'),
(7, 'Intel'),
(8, 'AMD'),
(9, 'Gigabyte'),
(10, 'ASRock'),
(11, 'MSI'),
(12, 'Kingston'),
(13, 'G.SKILL'),
(14, 'Crucial'),
(15, 'Toshiba'),
(16, 'Seagate'),
(17, 'Western Digital'),
(18, 'Adata'),
(19, 'Samsung'),
(20, 'MS Industrial'),
(21, 'LC Power'),
(22, 'COOLER MASTER'),
(24, 'Antec'),
(25, 'NZXT');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `cart_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `ordered_quantity` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`cart_id`, `user_id`, `product_id`, `order_id`, `ordered_quantity`) VALUES
(98, 5, 2, 91, 1),
(99, 5, 15, 91, 1),
(100, 5, 14, 92, 1),
(101, 5, 25, 92, 2),
(102, 2, 8, 93, 1),
(103, 1, 43, 94, 2),
(104, 1, 31, 94, 5),
(105, 1, 39, 95, 1),
(106, 1, 25, 95, 1),
(107, 4, 7, 96, 1),
(108, 4, 49, 97, 1),
(109, 4, 34, 97, 1),
(110, 4, 39, 97, 2),
(111, 4, 43, 97, 1),
(112, 8, 11, 98, 1),
(113, 8, 6, 98, 2),
(114, 9, 52, 99, 1),
(115, 9, 42, 99, 2),
(116, 9, 32, 99, 3),
(117, 9, 16, 100, 1),
(118, 9, 21, 100, 1),
(119, 2, 27, 101, 1),
(125, 2, 17, 101, 1),
(126, 2, 22, 101, 1),
(127, 3, 43, 102, 3),
(128, 3, 39, 102, 2),
(129, 3, 25, 102, 3),
(130, 3, 28, 102, 2),
(131, 3, 14, 102, 1),
(133, 5, 1, 103, 2),
(134, 5, 9, 104, 2),
(135, 5, 34, 105, 5);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_id`, `category`, `image`) VALUES
(1, 'Laptop računari', 'laptop_racunari.png'),
(2, 'Računari', 'racunari.png'),
(3, 'Procesori', 'procesori.png'),
(4, 'Matične ploče', 'maticne_ploce.png'),
(5, 'Grafičke kartice', 'graficke_kartice.png'),
(6, 'RAM memorije', 'ram_memorije.png'),
(7, 'Hard diskovi', 'hard_diskovi.png'),
(8, 'SSD', 'ssd.png'),
(9, 'Napajanja', 'napajanja.png'),
(10, 'Kućišta', 'kucista.png'),
(11, 'Hladnjaci i oprema', '1338ecb46d30bf1df36ffa0bd3980cc5.png');

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `rating` int(11) NOT NULL,
  `comment` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`comment_id`, `user_id`, `product_id`, `rating`, `comment`) VALUES
(1, 9, 1, 4, 'Veoma dobar laptop za ovu cenu'),
(2, 8, 1, 3, 'Laptop je solidan, ali nista vise od toga'),
(3, 1, 1, 5, 'Veoma dobra kupovina za ove pare'),
(4, 1, 2, 4, 'Vrhunski kvalitet izrade, jedino bit center trebalo da bude malo niza'),
(5, 1, 15, 5, 'Izuzetno dobar procesor'),
(6, 1, 14, 4, 'Odlican'),
(7, 1, 19, 3, 'Za ovaj novac je solidna'),
(8, 5, 1, 5, 'cg'),
(9, 5, 2, 5, 'nzjsjsksksk'),
(10, 5, 43, 5, 'hhj');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `city` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `zip_code` int(11) NOT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `in_total` decimal(10,2) NOT NULL,
  `date` date NOT NULL DEFAULT current_timestamp(),
  `status` tinyint(4) NOT NULL DEFAULT 0,
  `show_order` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `city`, `zip_code`, `address`, `phone`, `in_total`, `date`, `status`, `show_order`) VALUES
(91, 'Beograd', 11000, 'Cara Nikolaja 13', '0639863257', 152998.00, '2018-09-15', 2, 1),
(92, 'Beograd', 11000, 'Cara Nikolaja 13', '0639863257', 35997.00, '2018-09-15', 2, 0),
(93, 'Beograd', 11000, 'Hercegovacka 16', '0653231589', 36999.00, '2018-09-15', 0, 1),
(94, 'Beograd', 11000, 'Klinska 17', '0654123587', 198393.00, '2018-09-16', 0, 1),
(95, 'Beograd', 11000, 'Klinska 17', '0654123587', 14898.00, '2018-09-16', 1, 1),
(96, 'Beograd', 11000, 'Belaricka 234', '0649536782', 449999.00, '2018-09-16', 0, 1),
(97, 'Beograd', 11000, 'Belaricka 234', '0649536782', 21395.00, '2018-09-16', 0, 1),
(98, 'Beograd', 11000, 'Kumodraska 57', '0638156932', 177997.00, '2018-09-16', 0, 1),
(99, 'Novi Sad', 21000, 'Karadjordjeva 2', '0625832189', 251594.00, '2018-09-17', 0, 1),
(100, 'Novi Sad', 21000, 'Karadjordjeva 2', '0625832189', 44998.00, '2018-09-17', 0, 1),
(101, 'Beograd', 11000, 'Vladimira Mitrovica 110', '0639645832', 166997.00, '2018-09-17', 0, 1),
(102, 'Cacak', 32000, 'Svetog Save 67', '0695238512', 82389.00, '2018-09-17', 0, 1),
(103, 'Beograd', 11000, 'Cara Nikolaja 13', '0639863257', 79998.00, '2018-09-17', 0, 1),
(104, 'Beograd', 11000, 'Cara Nikolaja 13', '0639863257', 81980.00, '2018-09-18', 1, 1),
(105, 'Beograd', 11000, 'Cara Nikolaja 13', '0639863257', 27495.00, '2018-09-19', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `brand_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `featured` tinyint(4) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `title`, `price`, `brand_id`, `category_id`, `image`, `description`, `featured`, `quantity`) VALUES
(1, 'ACER Aspire 3 A315-51-37N3', 39999.00, 1, 1, 'd9786562d015873e7ca8734cec23fbb6.png', 'RAM memorija: 4GB\r\nEkran: 15.6\" 1366 x 768 piksela\r\nProcesor: i3 6006U\r\nGrafička karta: Intel HD 520\r\nHDD: 500GB\r\nBaterija: 2 ćelije\r\nBoja: Crna\r\nTežina (kg): 2.10', 1, 34),
(2, 'APPLE MacBook Air 13\"', 129999.00, 3, 1, 'apple1.png', 'RAM memorija: 8GB\r\nEkran: 13.3\" 1440 x 900 piksela\r\nProcesor: i5 5350u\r\nGrafička karta: Intel HD 6000\r\nSSD: 128GB\r\nBaterija: Lithium Polymer\r\nBoja: Srebrna\r\nTežina (kg): 1.35', 1, 50),
(5, 'ACER Aspire 7 A717-71G-520C', 100990.00, 1, 1, 'acer2.png', 'RAM memorija: 8GB\r\nEkran: 17.3\" 1920 x 1080 piksela\r\nProcesor: i5 7300HQ\r\nGrafička karta: nVidia GeForce GTX 1050 Ti sa 4GB DDR5\r\nHDD: 1TB\r\nBaterija: 4 ćelije\r\nBoja: Crna\r\nTežina (kg): 2.90', 0, 15),
(6, 'LENOVO IdeaPad 320-15ISK', 53999.00, 4, 1, 'lenovo1.png', 'RAM memorija: 8GB\r\nEkran: 15.6\" 1920 x 1080 piksela\r\nProcesor: i3 6006U\r\nGrafička karta: nVidia GeForce 920MX sa 2GB DDR3\r\nHDD: 1TB\r\nBaterija: 2 ćelije\r\nBoja: Siva\r\nTežina (kg): 2.20', 0, 60),
(7, 'DELL Alienware 15 R3', 449999.00, 5, 1, 'dell1.png', 'RAM memorija: 32GB\r\nEkran: 15.6\" 1920 x 1080 piksela\r\nProcesor: i7 7820HK\r\nGrafička karta: nVidia GeForce GTX 1080 sa 8GB DDR5\r\nHDD: 1TB\r\nBaterija: 6 ćelije\r\nBoja: Srebrna\r\nTežina (kg): 3.50', 0, 11),
(8, 'HP 260 G2 Desktop Mini PC', 36999.00, 6, 2, 'hp1.png', 'RAM memorija: 4GB\r\nProcesor: Intel® Pentium® 4405U\r\nGrafička karta: Intel® HD Graphics 510\r\nHDD: 500GB\r\nKućište: HP mini\r\nNapajanje: 65W\r\nOperativni sistem: FreeDOS\r\nTežina (kg): 1.05', 1, 21),
(9, 'ASUS VivoPC E420-B058Z', 40990.00, 2, 2, 'asus4.png', 'RAM memorija: 2GB\r\nProcesor: Intel® Celeron® 3865U\r\nGrafička karta: Intel® HD Graphics 610\r\nHDD: 500GB\r\nKućište: Asus mini\r\nNapajanje: 65W\r\nOperativni sistem: Windows 10 Home 64bit\r\nTežina (kg): 0.7', 0, 0),
(10, 'LENOVO V520-15IKL', 44999.00, 4, 2, 'lenovo2.png', 'RAM memorija: 4GB\r\nProcesor: Intel® Pentium® G4560\r\nGrafička karta: Intel® HD Graphics 610\r\nHDD: 500GB\r\nNapajanje: 180W', 0, 5),
(11, 'DELL OptiPlex 3050 MT', 69999.00, 5, 2, 'dell2.png', 'RAM memorija: 4GB\r\nProcesor: Intel® Core™ i3-7100\r\nGrafička karta: Intel® HD Graphics 630\r\nHDD: 500GB\r\nKućište: Dell Mini Tower\r\nNapajanje: 240W\r\nOperativni sistem: Windows 10 Pro 64bit\r\nTežina (kg): 7.9', 1, 12),
(12, 'OMEN by HP Desktop PC - 880-047ny', 149999.00, 6, 2, 'hp2.png', 'RAM memorija: 16GB\r\nProcesor: AMD Ryzen 7 1700\r\nGrafička karta: AMD Radeon RX 580\r\nHDD: 2TB\r\nSSD: 128GB\r\nNapajanje: 500W\r\nOperativni sistem: FreeDOS 2.0\r\nTežina (kg): 17.6', 0, 12),
(13, 'INTEL Pentium G4400', 6599.00, 7, 3, 'intel1.png', 'Podnožje: Intel® 1151\r\nBroj jezgara: 2\r\nThreads: 2\r\nTehnologija izrade: 14 nm\r\nTDP: 54W\r\nRadna frekvencija: 3.3GHz\r\nTurbo frekvencija: Nema\r\nL2: 2 x 256 KB\r\nL3: 3MB', 0, 56),
(14, 'INTEL Core i3-8100', 14999.00, 7, 3, 'intel2.png\r\n', 'Podnožje: Intel® 1151\r\nBroj jezgara: 4\r\nThreads: 4\r\nTehnologija izrade: 14 nm\r\nTDP: 65W\r\nRadna frekvencija: 3.6GHz\r\nTurbo frekvencija: Nema\r\nL2: 2 x 256 KB\r\nL3: 6MB', 1, 46),
(15, 'AMD Ryzen 5 2600', 22999.00, 8, 3, 'amd1.png', 'Podnožje: AMD® AM4\r\nBroj jezgara: 6\r\nThreads: 12\r\nTehnologija izrade: 12 nm\r\nTDP: 65W\r\nRadna frekvencija: 3.4GHz\r\nTurbo frekvencija: 3.9GHz\r\nL2: 3MB\r\nL3: 16MB', 1, 48),
(16, 'AMD Ryzen 7 2700', 35999.00, 8, 3, 'amd2.png', 'Podnožje: AMD® AM4\r\nBroj jezgara: 8\r\nThreads: 16\r\nTehnologija izrade: 12 nm\r\nTDP: 65W\r\nRadna frekvencija: 3.2GHz\r\nTurbo frekvencija: 4.1GHz\r\nL2: 4MB\r\nL3: 16MB', 0, 34),
(17, 'INTEL Core i7-8700K', 47999.00, 7, 3, 'intel3.png', 'Podnožje: Intel® 1151\r\nBroj jezgara: 6\r\nThreads: 12\r\nTehnologija izrade: 14 nm\r\nTDP: 95W\r\nRadna frekvencija: 3.7GHz\r\nTurbo frekvencija: 4.7GHz\r\nL2: 6 x 256 KB\r\nL3: 12MB', 0, 38),
(18, 'ASUS H110M-R/C/SI', 5999.00, 2, 4, 'asus3.png', 'Podnožje: Intel® 1151\r\nČipset: Intel® H110\r\nFormat ploče: Micro ATX', 0, 51),
(19, 'GIGABYTE GA-A320M-S2H', 6499.00, 9, 4, 'gigabyte2.png', 'Podnožje: AMD® AM4\r\nČipset: AMD® A320\r\nFormat ploče: ATX', 1, 51),
(20, 'ASRock H310M-HDV', 7999.00, 10, 4, 'asrock1.png\r\n', 'Podnožje: Intel® 1151\r\nČipset: Intel® H310\r\nFormat ploče: Micro ATX', 0, 16),
(21, 'MSI B350M PRO-VD', 8999.00, 11, 4, 'msi3.png', 'Podnožje: AMD® AM4\r\nČipset: AMD® B350\r\nFormat ploče: Micro ATX', 1, 64),
(22, 'GIGABYTE B360M D2V', 93999.00, 9, 4, 'gigabyte3.png', 'Podnožje: Intel® 1151\r\nČipset: Intel® B360\r\nFormat ploče: Micro ATX', 0, 14),
(23, 'KINGSTON 4GB DDR3', 4999.00, 12, 6, 'kingston1.png', 'Tip: DDR3\r\nKapacitet: 4GB\r\nMaksimalna frekvencija: 1333Mhz\r\nLatencija: CL9', 0, 14),
(24, 'KINGSTON 4GB DDR3L HyperX FURY', 5199.00, 12, 6, 'kingston2.png', 'Tip: DDR3L\r\nKapacitet: 4GB\r\nMaksimalna frekvencija: 1866Mhz\r\nLatencija: CL11', 0, 54),
(25, 'CRUCIAL 8GB DDR4 2400MHz', 10499.00, 14, 6, 'crucial1.png', 'Tip: DDR4\r\nKapacitet: 8GB\r\nMaksimalna frekvencija: 2400Mhz\r\nLatencija: CL17', 1, 30),
(26, 'G.SKILL Value 8GB DDR4 2400MHz', 11999.00, 13, 6, 'gskill1.png', 'Tip: DDR4\r\nKapacitet: 8GB\r\nMaksimalna frekvencija: 2400Mhz\r\nLatencija: CL17', 0, 15),
(27, 'HYPERX Fury Black 16GB kit', 24999.00, 12, 6, 'kingston3.png', 'Tip: DDR4\r\nKapacitet: 16GB\r\nMaksimalna frekvencija: 2666Mhz\r\nLatencija: CL16', 1, 27),
(28, 'MSI - GT 1030', 10999.00, 11, 5, 'msi1.png', 'Proizvodjač čipa: Nvidia\r\nTip memorije: GDDR5\r\nKoličina memorije: 2GB\r\nMagistrala memorije: 64bit\r\nBrzina memorije: 6008 MHz\r\nBrzina GPU: 1518 MHz / 1265 MHz', 1, 82),
(29, 'MSI Radeon RX 550 2GB', 15499.00, 11, 5, 'msi2.png', 'Proizvodjač čipa: AMD Radeon\r\nTip memorije: GDDR5\r\nKoličina memorije: 2GB\r\nMagistrala memorije: 128bit\r\nBrzina memorije: 7000 MHz\r\nBrzina GPU: 1203 MHz', 0, 52),
(30, 'ASUS GeForce GTX 1050 Ti', 21999.00, 2, 5, 'asus1.png', 'Proizvodjač čipa: Nvidia\r\nTip memorije: GDDR5\r\nKoličina memorije: 4GB\r\nMagistrala memorije: 128bit\r\nBrzina memorije: 7008 MHz\r\nBrzina GPU: 1366 MHz / 1480 MHz', 0, 26),
(31, 'ASUS Radeon RX 570', 38999.00, 2, 5, 'asus2.png', 'Proizvodjač čipa: AMD Radeon\r\nTip memorije: GDDR5\r\nKoličina memorije: 4GB\r\nMagistrala memorije: 256bit\r\nBrzina memorije: 7000 MHz\r\nBrzina GPU: 1266 MHz', 1, 52),
(32, 'GIGABYTE nVidia GeForce GTX 1070 Ti', 69999.00, 9, 5, 'gigabyte1.png', 'Proizvodjač čipa: Nvidia\r\nTip memorije: GDDR5\r\nKoličina memorije: 8GB\r\nMagistrala memorije: 256bit\r\nBrzina memorije: 8008 MHz\r\nBrzina GPU: 1632 MHz / 1721 MHz', 0, 33),
(33, 'TOSHIBA 500GB 3.5\"', 4999.00, 15, 7, 'toshiba1.png', 'Format: 3.5\"\r\nKonekcija: SATA III\r\nKapacitet: 500GB HDD\r\nBroj obrtaja: 7200 RPM\r\nBuffer: 64 MB', 0, 55),
(34, 'SEAGATE 1TB, 3.5\"', 5499.00, 16, 7, 'seagate1.png', 'Format: 3.5\"\r\nKonekcija: SATA III\r\nKapacitet: 1TB HDD\r\nBroj obrtaja: 7200 RPM\r\nBuffer: 64 MB', 1, 9),
(35, 'WD 500GB SATA III', 5999.00, 17, 7, 'wd1.png', 'Format: 3.5\"\r\nKonekcija: SATA III\r\nKapacitet: 500GB HDD\r\nBroj obrtaja: 7200 RPM\r\nBuffer: 32 MB', 0, 6),
(36, 'WD 2TB SATA III', 8999.00, 17, 7, 'wd2.png\r\n', 'Format: 3.5\"\r\nKonekcija: SATA III\r\nKapacitet: 2TB HDD\r\nBroj obrtaja: 5400 RPM\r\nBuffer: 64 MB', 1, 47),
(37, 'SEAGATE 4TB, 3.5\"', 12999.00, 16, 7, 'seagate2.png\r\n', 'Format: 3.5\"\r\nKonekcija: SATA III\r\nKapacitet: 4TB HDD\r\nBroj obrtaja: 5900 RPM\r\nBuffer: 256 MB', 0, 17),
(38, 'KINGSTON A400 120GB', 3499.00, 12, 8, 'kingston4.png', 'Format: 2.5\'\'\r\nInterfejs: SATA III\r\nKapacitet: 120GB\r\nBrzina čitanja: do 500 MB/s\r\nBrzina pisanja: do 320 MB/s', 0, 79),
(39, 'ADATA Ultimate SU650 120GB', 4399.00, 18, 8, 'adata1.png', 'Format: 2.5\'\'\r\nInterfejs: SATA III\r\nKapacitet: 120GB\r\nBrzina čitanja: do 520 MB/s\r\nBrzina pisanja: do 320 MB/s', 1, 10),
(40, 'KINGSTON UV400 120GB', 4999.00, 12, 8, 'kingston5.png', 'Format: 2.5\'\'\r\nInterfejs: SATA III\r\nKapacitet: 120GB\r\nBrzina čitanja: do 550 MB/s\r\nBrzina pisanja: do 350 MB/s', 0, 46),
(41, 'ADATA Ultimate SU800 256GB', 8799.00, 18, 8, 'adata2.png', 'Format: 2.5\'\'\r\nInterfejs: SATA III\r\nKapacitet: 256GB\r\nBrzina čitanja: do 560 MB/s\r\nBrzina pisanja: do 520 MB/s', 0, 6),
(42, 'SAMSUNG 860 Pro 256GB', 15999.00, 19, 8, 'samsung1.png\r\n', 'Format: 2.5\'\'\r\nInterfejs: SATA III\r\nKapacitet: 256GB\r\nBrzina čitanja: do 560 MB/s\r\nBrzina pisanja: do 530 MB/s', 1, 30),
(43, 'MS INDUSTRIAL MS-500', 1699.00, 20, 9, 'ms3.png', 'Tip: Standardno\r\nOblik: ATX (PS2)\r\nIzlazna snaga: 500W\r\nPFC: Aktivno', 1, 92),
(44, 'LC-Power 600W LC600H-12', 4699.00, 21, 9, 'lcpower2.png', 'Tip: Standardno\r\nOblik: ATX (PS2)\r\nIzlazna snaga: 600W\r\nPFC: Aktivno', 0, 14),
(45, 'COOLER MASTER 500W ELITE V3', 5799.00, 22, 9, 'cm3.png', 'Tip: Standardno\r\nOblik: ATX (PS2)\r\nIzlazna snaga: 500W\r\nPFC: Aktivno', 1, 25),
(46, 'MS INDUSTRIAL PLATINUM PRO 80PLUS 600W', 6599.00, 20, 9, 'ms4.png', 'Tip: Standardno\r\nOblik: ATX (PS2)\r\nIzlazna snaga: 600W\r\nPFC: Aktivno', 0, 5),
(47, 'COOLER MASTER MWE GOLD 550', 13999.00, 22, 9, 'cm4.png', 'Tip: Standardno\r\nOblik: ATX (PS2)\r\nIzlazna snaga: 550W\r\nPFC: Aktivno', 0, 63),
(48, 'MS INDUSTRIAL HUNTER ', 1699.00, 20, 10, 'ms1.png', 'Tip: Midi Tower\r\nKompatibilnost: Micro-ATX, Mini-ITX, ATX\r\nNapajanje: Nema', 0, 15),
(49, 'LC POWER LC-1401MI', 5399.00, 21, 10, 'lcpower1.png', 'Tip: Mini ITX\r\nKompatibilnost: Micro-ATX, Mini-ITX\r\nNapajanje: LC POWER 200W LC200SFX V3.21', 1, 51),
(50, 'MS INDUSTRIAL DARK SHADOW', 5499.00, 20, 10, 'ms2.png', 'Tip: Midi Tower\r\nKompatibilnost: Micro-ATX, Mini-ITX, ATX\r\nNapajanje: Nema', 0, 23),
(51, 'COOLER MASTER MASTERBOX LITE 3.1', 6499.00, 22, 10, 'cm1.png', 'Tip: Micro Tower\r\nKompatibilnost: Micro-ATX, Mini-ITX\r\nNapajanje: Nema', 1, 15),
(52, 'COOLER MASTER MASTERBOX Q300P', 9599.00, 22, 10, 'cm2.png', 'Tip: Mini Tower\r\nKompatibilnost: Micro-ATX, Mini-ITX\r\nNapajanje: Nema', 0, 144),
(53, 'Termalna pasta MasterGel', 1199.00, 22, 11, 'd10670483497572b14c193a4fa9c2c78.png', 'Tip: Paste za hladnjake\r\nNamena: Za centralne (CPU) i grafičke procesore (GPU)\r\nTermalna provodljivost: >5 W/m-K\r\nZapremina: 1.5 ml\r\nBoja: bela', 0, 125),
(54, 'ANTEC cpu kuler C40', 3599.00, 24, 11, '67739a3dcb9c927d501ef1ebd3f54550.png', 'Tip: CPU\r\nTip hlađenja: Vazdušno hlađenje\r\nPodržana podnožja procesora: Intel®: LGA1366/LGA1156/LGA1155/LGA1151/LGA1150/LGA775, AMD™: FM2+/FM2/FM1/AM3+/AM3/AM2+/AM2\r\nOdvod toplote (Heatpipe): Četri toplotne cevi i bakarna ploča na kontaktu sa procesoru\r\nBroj obrtaja: 800~2200± 10% RPM\r\nProtok vazduha: 38 CFM\r\nNivo buke: 16-23 dB (A)\r\nKonektor: 4 pin\r\nDimenzije: 136.5 x 100 x 77 mm\r\nTežina: 540 g', 0, 54),
(55, 'MASTERFAN PRO 120', 7499.00, 22, 11, '21a0bc230aaebf8eb5d86421ffe6849d.png', 'Tip hlađenja: Vazdušno hlađenje\r\nBroj obrtaja: 650-1,500 RPM ± 10%\r\nProtok vazduha: 35 CFM ± 10%\r\nPritisak vazduha: 1.45 mmH2O ± 10%\r\nNivo buke: 6 - 20 dBA\r\nVoltaža: 12 VDC\r\nKonektor: 4-Pin\r\nTežina: 162 g\r\nOstalo: Tri ventilatora sa RGB LED kontrolerom.', 0, 56),
(56, 'MasterAir Maker 8', 17999.00, 22, 11, 'fb58506cfeeb8851007011fc7d2a93d1.png', 'Tip: CPU\r\nTip hlađenja: Vazdušno hlađenje\r\nPodržana podnožja procesora: Intel® LGA 2011-v3 / 2011 / 1366 / 1156 / 1155 / 1151 / 1150 / 775, AMD FM2+ / FM2 / FM1 / AM3+ / AM3 / AM2+\r\nOdvod toplote (Heatpipe): 8 toplotnih cevi\r\nVentilator: 140 x 25 mm x 2kom\r\nBroj obrtaja: 600 – 1,800 RPM ± 10%\r\nProtok vazduha: 19.8 – 66 CFM ± 10%\r\nPritisak vazduha: 0.24 – 2.2 mmH2O\r\nNivo buke: 8~24 dBA\r\nVoltaža: 12 VDC\r\nKonektor: 4-Pin\r\nDimenzije: 135 x 145 x 172 mm (DxŠxV)\r\nTežina: 1350 g', 0, 15),
(57, 'NZXT KRAKEN X62', 19999.00, 25, 11, 'fea33b2764691cfe5ef634f555179b2c.png', 'Tip: CPU\r\nTip hlađenja: Vodeno hlađenje\r\nPodržana podnožja procesora: Intel Socket 1151, 1150, 1155, 1156, 1366, 2011, 2011-3, 2066, AMD Socket AM4, FM2+, FM2, FM1, AM3+, AM3, AM2+, AM2\r\nHladnjak: Aluminijum\r\nBrzina pumpe: 1,600~2,800 +/- 300RPM\r\nVentilator: 2x Aer P140\r\nBroj obrtaja: 500~1,800 +/- 300RPM\r\nNivo buke: ventilator: 21-38dBA\r\nDimenzije: Hladnjak: 315 x 143 x 30mm\r\nPumpa: 80 x 80 x 52.9mm\r\nTežina: 1.29kg', 0, 23);

-- --------------------------------------------------------

--
-- Table structure for table `shipping_address`
--

CREATE TABLE `shipping_address` (
  `shipping_address_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `city` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `zip_code` int(11) DEFAULT 0,
  `address` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `phone` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `shipping_address`
--

INSERT INTO `shipping_address` (`shipping_address_id`, `user_id`, `city`, `zip_code`, `address`, `phone`) VALUES
(1, 1, 'Beograd', 11000, 'Klinska 17', '0654123587'),
(2, 2, 'Beograd', 11000, 'Vladimira Mitrovica 110', '0639645832'),
(3, 3, '', 0, '', ''),
(4, 4, 'Beograd', 11000, 'Belaricka 234', '0649536782'),
(5, 5, 'Beograd', 11000, 'Cara Nikolaja 13', '0639863257'),
(8, 8, '', 0, '', ''),
(9, 9, 'Novi Sad', 21000, 'Karadjordjeva 2', '0625832189');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `full_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `role` tinyint(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `full_name`, `email`, `password`, `role`) VALUES
(1, 'Miloš Mastilović', 'milos@gmail.com', 'milos123', 0),
(2, 'Bozidar Mastilovic', 'boza@gmail.com', 'boza123', 0),
(3, 'Miljan Miljanic', 'miljan@gmail.com', 'miljan123', 0),
(4, 'Djordje Komlen', 'djole@gmail.com', 'djole123', 0),
(5, 'Pera Peric', 'pera@gmail.com', 'pera123', 0),
(8, 'Laza Lazarevic', 'laza@gmail.com', 'laza123', 0),
(9, 'Filip Filipovic', 'filip@gmail.com', 'filip123', 0),
(10, 'Milos Mastilovic', 'milos@gmail.com', 'milos123', 1),
(11, 'Bozidar Mastilovic', 'boza@gmail.com', 'boza123', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `brand`
--
ALTER TABLE `brand`
  ADD PRIMARY KEY (`brand_id`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`cart_id`) USING BTREE,
  ADD KEY `ProductID` (`product_id`),
  ADD KEY `UserID` (`user_id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `BrandID` (`brand_id`),
  ADD KEY `CategoryID` (`category_id`);

--
-- Indexes for table `shipping_address`
--
ALTER TABLE `shipping_address`
  ADD PRIMARY KEY (`shipping_address_id`),
  ADD KEY `shipping_address_ibfk_1` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `brand`
--
ALTER TABLE `brand`
  MODIFY `brand_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `cart_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=139;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `comment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=106;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT for table `shipping_address`
--
ALTER TABLE `shipping_address`
  MODIFY `shipping_address_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `cart_ibfk_3` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`brand_id`),
  ADD CONSTRAINT `product_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`);

--
-- Constraints for table `shipping_address`
--
ALTER TABLE `shipping_address`
  ADD CONSTRAINT `shipping_address_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
