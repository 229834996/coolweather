package com.coolweather.app.model;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.db.CoolWeatherOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {
	
	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "cool_weather";
	
	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;

	private SQLiteDatabase db;

	private static CoolWeatherDB coolWeatherDB;
	
	
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/**
	 * 获取CoolWeatherDB的实例
	 */
	public synchronized static CoolWeatherDB getInstance(Context context){
		if (coolWeatherDB == null) {
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/**
	 * 将Province实例存储到数据库
	 */
	public void saveProvince(Province province){
		
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	/**
	 * 从数据库读取全国所有的省份信息
	 */
	public List<Province> loadProvince(){
		
		List<Province> list = new ArrayList<Province>();
		
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				province.setId(id);
				String province_name = cursor.getString(cursor.getColumnIndex("province_name"));
				province.setProvinceName(province_name);
				String province_code = cursor.getString(cursor.getColumnIndex("province_code"));
				province.setProvinceCode(province_code);
				list.add(province);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
	
	/**
	 * 将City实例存储在数据库
	 */
	public void saveCity(City city){
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	
	/**
	 * 从数据库读取某省下所有的城市信息
	 */
	public List<City> loadCity(int provinceId){
		
		List<City> list = new ArrayList<City>();
		
		Cursor cursor = db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				city.setId(id);
				String city_name = cursor.getString(cursor.getColumnIndex("city_name"));
				city.setCityName(city_name);
				String city_code = cursor.getString(cursor.getColumnIndex("city_code"));
				city.setCityCode(city_code);
				city.setProvinceId(provinceId);
				list.add(city);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
	
	/**
	 * 将Country实例存储到数据库
	 */
	public void saveCountry(Country country){
		if (country != null) {
			ContentValues values = new ContentValues();
			values.put("country_name", country.getCountryName());
			values.put("country_code", country.getCountryCode());
			values.put("city_id", country.getCityId());
			db.insert("Country", null, values);
		}
	}
	
	/**
	 * 从数据库读取某城市下所有的县信息
	 */
	public List<Country> loadCountry(int cityId){
		
		List<Country> list = new ArrayList<Country>();
		
		Cursor cursor = db.query("Country", null, "city_id = ?", new String[]{String.valueOf(cityId)}, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				Country country = new Country();
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				country.setCityId(id);
				String country_name = cursor.getString(cursor.getColumnIndex("country_name"));
				country.setCountryName(country_name);
				String country_code = cursor.getString(cursor.getColumnIndex("country_code"));
				country.setCountryCode(country_code);
				country.setCityId(cityId);
				list.add(country);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
}
