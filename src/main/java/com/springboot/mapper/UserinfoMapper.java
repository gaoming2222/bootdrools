package com.springboot.mapper;


import java.util.List;

import com.springboot.entity.Userinfo;

public interface UserinfoMapper {

	/**获取所有用户信息
	 * @return
	 */
	public List<Userinfo> getAllUserinfo();
}
