﻿var names_Locale = new Array();
var weekdays_Locale = new Array();
var days_Locale = new Array();
var none_Locale = new Array();
var indefinite_Locale = new Array();
var month_abbrev_Locale = new Array();
var month_abbrev_Locale_Java = new Array();

// add your locale setting here
names_Locale["en_US"] = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
// we have two sunday to accomodate for calendars starting on monday
weekdays_Locale["en_US"] = new Array('S', 'M', 'T', 'W', 'T', 'F', 'S', 'S');
days_Locale["en_US"]  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
none_Locale["en_US"] = "None";
indefinite_Locale["en_US"] = "Indefinite";
// setting month_abrev to null is equal to set the month abrev to first 3 letters of the names_Locales
month_abbrev_Locale["en_US"] = null;
/*This should match with Java code*/
month_abbrev_Locale_Java["en_US"] = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");

names_Locale["en_AU"] = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
//we have two sunday to accomodate for calendars starting on monday
weekdays_Locale["en_AU"] = new Array('S', 'M', 'T', 'W', 'T', 'F', 'S', 'S');
days_Locale["en_AU"]  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
none_Locale["en_AU"] = "None";
indefinite_Locale["en_AU"] = "Indefinite";
//setting month_abrev to null is equal to set the month abrev to first 3 letters of the names_Locales
month_abbrev_Locale["en_AU"] = null;
/*This should match with Java code*/
month_abbrev_Locale_Java["en_AU"] = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");

names_Locale["en_GB"] = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
//we have two sunday to accomodate for calendars starting on monday
weekdays_Locale["en_GB"] = new Array('S', 'M', 'T', 'W', 'T', 'F', 'S', 'S');
days_Locale["en_GB"]  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
none_Locale["en_GB"] = "None";
indefinite_Locale["en_GB"] = "Indefinite";
//setting month_abrev to null is equal to set the month abrev to first 3 letters of the names_Locales
month_abbrev_Locale["en_GB"] = null;
/*This should match with Java code*/
month_abbrev_Locale_Java["en_GB"] = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");

names_Locale["en_IE"] = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
//we have two sunday to accomodate for calendars starting on monday
weekdays_Locale["en_IE"] = new Array('S', 'M', 'T', 'W', 'T', 'F', 'S', 'S');
days_Locale["en_IE"]  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
none_Locale["en_IE"] = "None";
indefinite_Locale["en_IE"] = "Indefinite";
//setting month_abrev to null is equal to set the month abrev to first 3 letters of the names_Locales
month_abbrev_Locale["en_IE"] = null;
/*This should match with Java code*/
month_abbrev_Locale_Java["en_IE"] = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");


names_Locale["de_DE"] = new Array("Januar","Februar","März","April","Mai","Juni","Juli","August","September","Oktober","November","Dezember");
// we have two sunday to accomodate for calendars starting on monday
//weekdays_Locale["de_DE"] = new Array('Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag', 'Sonntag');
weekdays_Locale["de_DE"] = new Array('Son', 'Mon', 'Die', 'Mit', 'Don', 'Fre', 'Sam', 'Son');
days_Locale["de_DE"]  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
none_Locale["de_DE"] = "keine";
indefinite_Locale["de_DE"] = "unbegrenzt";
month_abbrev_Locale["de_DE"] = new Array("Jan","Feb","Mär","Apr","Mai","Jun","Jul","Aug","Sep","Okt","Nov","Dez");
/*This should match with Java code*/
month_abbrev_Locale_Java["de_DE"] = new Array("Jan","Feb","Mär","Apr","Mai","Jun","Jul","Aug","Sep","Okt","Nov","Dez");

names_Locale["pl_PL"] = new Array("Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień");
// we have two sunday to accomodate for calendars starting on monday
weekdays_Locale["pl_PL"] = new Array('N', 'P', 'W', 'Ś', 'C', 'P', 'S', 'N');
days_Locale["pl_PL"]  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
none_Locale["pl_PL"] = "None";
indefinite_Locale["pl_PL"] = "Nieokreślony";
month_abbrev_Locale["pl_PL"] = new Array("Sty","Lut","Mar","Kwi","Maj","Cze","Lip","Sie","Wrz","Paź","Lis","Gru");
/*This should match with Java code*/
month_abbrev_Locale_Java["pl_PL"] = new Array("sty","lut","mar","kwi","maj","cze","lip","sie","wrz","paź","lis","gru");

// add your locale setting here  
names_Locale["fr_FR"] = new Array("janvier","février","mars","avril","mai","juin","juillet","août","septembre","octobre","novembre","décembre");
// we have two sunday to accomodate for calendars starting on monday
weekdays_Locale["fr_FR"] = new Array('D', 'L', 'M', 'M', 'J', 'V', 'S', 'D');
days_Locale["fr_FR"]  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
none_Locale["fr_FR"] = "Aucune";
indefinite_Locale["fr_FR"] = "indéfini";
// setting month_abrev to null is equal to set the month abrev to first 3 letters of the names_Locales
month_abbrev_Locale["fr_FR"] = null;
/*This should match with Java code*/
month_abbrev_Locale_Java["fr_FR"] = new Array("janv.","févr.","mars","avr.","mai","juin","juil.","août","sept.","oct.","nov.","déc.");

// add your locale setting here
names_Locale["it_IT"] = new Array("Gennaio","Febbraio","Marzo","Approvatore","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre");
// we have two sunday to accomodate for calendars starting on monday
weekdays_Locale["it_IT"] = new Array('D', 'L', 'M', 'M', 'G', 'V', 'S', 'D');
days_Locale["it_IT"]  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
none_Locale["it_IT"] = "None";
indefinite_Locale["it_IT"] = "Indefinito";
// setting month_abrev to null is equal to set the month abrev to first 3 letters of the names_Locales
month_abbrev_Locale["it_IT"] = null;
/*This should match with Java code*/
month_abbrev_Locale_Java["it_IT"] = new Array("gen","feb","mar","apr","mag","giu","lug","ago","set","ott","nov","dic");

// turkey
names_Locale["tr_TR"] = new Array("Ocak","Şubat","Mart","Nisan","Mayıs","Haziran","Temmuz","Ağustos","Eylül","Ekim","Kasım","Aralık");
//we have two sunday to accomodate for calendars starting on monday
weekdays_Locale["tr_TR"] = new Array('Paz', 'Pzt', 'Sal', 'Çar', 'Per', 'Cum', 'Cmt', 'Paz');
days_Locale["tr_TR"]  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
none_Locale["tr_TR"] = "None";
indefinite_Locale["tr_TR"] = "Belirsiz";
//setting month_abrev to null is equal to set the month abrev to first 3 letters of the names_Locales
month_abbrev_Locale["tr_TR"] = null;
/*This should match with Java code*/
month_abbrev_Locale_Java["tr_TR"] = new Array("Oca","Şub","Mar","Nis","May","Haz","Tem","Ağu","Eyl","Eki","Kas","Ara");

// Simplified Chinese
names_Locale["zh_CN"] = new Array("一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月");
// we have two sunday to accomodate for calendars starting on monday
weekdays_Locale["zh_CN"] = new Array('日', '一', '二', '三', '四', '五', '六', '日');
days_Locale["zh_CN"]  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
none_Locale["zh_CN"] = "无";
indefinite_Locale["zh_CN"] = "不确定";
// setting month_abrev to null is equal to set the month abrev to first 3 letters of the names_Locales
month_abbrev_Locale["zh_CN"] = null;
/*This should match with Java code*/
month_abbrev_Locale_Java["zh_CN"] = new Array("一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月");

names_Locale["ar_AE"] = new Array("يناير","فبراير","مارس","أبريل/إبريل","مايو","يونيو/يونيه","يوليو/يوليه","أغسطس","سبتمبر","أكتوبر","نوفمبر","ديسمبر");
// we have two sunday to accomodate for calendars starting on monday
weekdays_Locale["ar_AE"] = new Array('ح', 'ن', 'ث', 'ر', 'خ', 'ج', 'س', 'ح');
days_Locale["ar_AE"]  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
none_Locale["ar_AE"] = "لا شيء";
indefinite_Locale["ar_AE"] = "غير محدد";
// setting month_abrev to null is equal to set the month abrev to first 3 letters of the names_Locales
month_abbrev_Locale["ar_AE"] = null;
/*This should match with Java code*/
month_abbrev_Locale_Java["ar_AE"] = new Array("ينا","فبر","مار","أبر","ماي","يون","يول","أغس","سبت","أكت","نوف","ديس");
//month_abbrev_Locale_Java["ar_AE"] = new Array("ینا","فبر","مار","أبر","ماي","یون","یول","أغس","سبت","أآت","نوف","دیس");

function MonthToInt(str) {
		var month_abbrev = month_abbrev_Locale_Java[pageLocale];
		var names = names_Locale[pageLocale];
		if( names == null ) {
			month_abbrev = month_abbrev_Locale_Java["en_US"];
			names = names_Locale["en_US"];
		}
			
		if( month_abbrev == null ) {
			for(i=0; i< names.length;i++) {
				if( names[i].substring(0,3) == str ) {
					if( i< 9 ) return '0'+(i+1);
						else 
					return ''+(i+1);
				}
			}
		}
		else {
			for(i=0; i< month_abbrev.length;i++) {
				if( month_abbrev[i] == str ) {
					if( i< 9 ) return '0'+(i+1);
						else 
					return ''+(i+1);
				}
			}
		}	
	return '00';
}	
       
function dateToIntString(strval) {
if(!strval)return "";
var info_array = strval.split("-");
return info_array[2] + MonthToInt(info_array[1]) + info_array[0] ;
}
