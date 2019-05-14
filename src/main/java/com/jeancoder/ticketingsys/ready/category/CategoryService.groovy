package com.jeancoder.ticketingsys.ready.category

import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.core.power.DatabasePower
import com.jeancoder.ticketingsys.ready.category.dto.CategoryListDto
import com.jeancoder.ticketingsys.ready.category.dto.TicCategoryDto
import com.jeancoder.ticketingsys.ready.category.entity.DataTcSsTicCategory

class CategoryService {
	public static final CategoryService INSTANCE = new CategoryService();
	
	public List<CategoryListDto> getAllCategoryList() {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "select id,category_code,category_name,a_time from data_tc_ss_tic_category where flag != ?";
		dbpower.doQueryList(CategoryListDto.class, sql, -1);
	}
	
	public void addOrUpdate(DataTcSsTicCategory category) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		dbpower.doUpdateSerialize( category, "id");
	}
	
	public void deleteCategory(Long id) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "UPDATE data_tc_ss_tic_category set flag = -1 where id  = ?";
		dbpower.doUpdate(sql, id);
	}
	
	public TicCategoryDto getCategoryById(Long id) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "select id,category_code,category_name,a_time from data_tc_ss_tic_category where id = ?";
		dbpower.doQueryUnique(TicCategoryDto.class, sql, id);
	}
	
	public boolean checkCodeExists(String code) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT id FROM data_tc_ss_tic_category WHERE flag = ? AND category_code = ?";
		Long existsId = dbpower.doQueryUniqueScalar(Long.class, sql,-1,code);
		print existsId
		return existsId != null;
	}
}
