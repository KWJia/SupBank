package com.supbank.base;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Comments 数据库操作服务封装,通过数据库参数配置的sql执行
 * @author jiayl
 * @version 0.1
 */
@Service(value = "DBService")
@Transactional
public class DBService {
	private static final Logger logger = LoggerFactory
			.getLogger(DBService.class);
	private static HashMap<String, List<DataRow>> tablefield = new HashMap<String, List<DataRow>>();// 表字段列表
	private static HashMap<String, String> codemap = new HashMap<String, String>();// codecode表中所有的SQL
	private static HashMap<String, List<DataRow>> fieldmap = new HashMap<String, List<DataRow>>();// 查询语句的表头设置
	private static DataRow<String,String> tradebpm = new DataRow<String,String>();//存放tradetype与对应处理函数的对应关系
	
	// sql日志管理开关
	private static boolean sqllogflag = true;
	// sql日志耗时阀值 默认10秒
	private static long costtime = 1000;
	// 堆栈预警开关
	private static boolean stackTraceFlag = false;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private Environment env;
	
	public String getTable_Schem()
	{
		try {
			String url = env.getProperty("spring.datasource.url");
			int startIndex = url.lastIndexOf("/");
			int endIndex = url.lastIndexOf("?");
			String table_schem = url.substring(startIndex,endIndex).replace("/","");
			return table_schem;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 通过sql的key和条件查询单条记录
	 */
	public DataRow querySimpleRowByName(String name, DataRow param)
			throws Exception {
		String sql = getSqlByCodeKey(name, param);
		return querySimpleRowBySql(sql);
	}

	/**
	 * 通过Sql查询单条记录
	 */
	public DataRow querySimpleRowBySql(String sql)   throws Exception {
		//logger.info("查询SQL：{}",sql);
		final DataRow data = new DataRow();
		long startsql = System.currentTimeMillis();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException{
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int column = 1; column <= rsmd.getColumnCount(); column++) {
					if (rsmd.getColumnType(column) == Types.BLOB
							|| rsmd.getColumnType(column) == Types.LONGVARBINARY) {
							Blob blob = rs.getBlob(column);
							try {
								data.put(rsmd.getColumnLabel(column), (new String(
										blob.getBytes(1, (int) blob.length()),
										"GBK")));
							} catch (UnsupportedEncodingException e) {
								throw new SQLException("数据库查询错误:"+e.getMessage());
							}
							// logger.debug(rsmd.getColumnName(column)+"="+data.getString(rsmd.getColumnName(column)));
					} else if(rsmd.getColumnType(column) == Types.DATE||
							rsmd.getColumnType(column) == Types.TIME||
							rsmd.getColumnType(column) == Types.TIMESTAMP){
						data.put(rsmd.getColumnLabel(column),
								rs.getObject(column));
					}else if(rsmd.getColumnType(column) == Types.CHAR||
							rsmd.getColumnType(column) == Types.LONGVARCHAR||
							rsmd.getColumnType(column) == Types.VARCHAR||
							rsmd.getColumnType(column) == Types.LONGNVARCHAR) {
						data.put(rsmd.getColumnLabel(column),
								rs.getString(column));
					}else{
						data.put(rsmd.getColumnLabel(column),
								rs.getObject(column));
					}
				}
				return ;
			}
		});
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(sql,sqlcost);
		return data;
	}
	
	/**
	 * 通过SQL查询带BLOB字段的记录
	 */
	public List<DataRow> queryBlobForList(String sql) {
		//logger.info("查询SQL：{}",sql);
		long startsql = System.currentTimeMillis();
		final ArrayList list = new ArrayList();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				DataRow data = new DataRow();
				ResultSetMetaData rsmd = rs.getMetaData();
				
				for (int column = 1; column <= rsmd.getColumnCount(); column++) {
					// logger.debug(rsmd.getColumnName(column)+" type="+rsmd.getColumnType(column));
					if (rsmd.getColumnType(column) == Types.BLOB
							|| rsmd.getColumnType(column) == Types.LONGVARBINARY) {
						try {
							Blob blob = rs.getBlob(column);
							data.put(rsmd.getColumnLabel(column), (new String(
									blob.getBytes(1, (int) blob.length()),
									"GBK")));
							// logger.debug(rsmd.getColumnName(column)+"="+data.getString(rsmd.getColumnName(column)));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					} else if(rsmd.getColumnType(column) == Types.DATE||
							rsmd.getColumnType(column) == Types.TIME||
							rsmd.getColumnType(column) == Types.TIMESTAMP){
						data.put(rsmd.getColumnLabel(column),
								rs.getObject(column));
					}else if(rsmd.getColumnType(column) == Types.CHAR||
							rsmd.getColumnType(column) == Types.LONGVARCHAR||
							rsmd.getColumnType(column) == Types.VARCHAR||
							rsmd.getColumnType(column) == Types.LONGNVARCHAR) {
						data.put(rsmd.getColumnLabel(column),
								rs.getString(column));
					}else{
						data.put(rsmd.getColumnLabel(column),
								rs.getObject(column));
					}
					
				}
				list.add(data);
			}
		});
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(sql,sqlcost);
		return list;
	}

	public List<DataRow> queryForList(String sql) {
		return this.queryBlobForList(sql);
	}

	/**
	 * 查询列表实体 sql全路径名，参数
	 */
	public List<DataRow> queryForListByCodeKey(String name,
			DataRow param) throws Exception {
		String sql = getSqlByCodeKey(name, param);
		//logger.debug(sql);
		return this.queryBlobForList(sql);
	}

	/**
	 * 查询单实体 sql全路径名，参数
	 */
	public DataRow queryForMapByCodeKey(String name, DataRow param)
			throws Exception {
		DataRow map = new DataRow();
		String sql = getSqlByCodeKey(name, param);
		//logger.debug(sql);
		try{
			map = querySimpleRowBySql(sql);
		}catch(EmptyResultDataAccessException e){
			return new DataRow();
		}
		return map;
	}

	// @Transactional
	/**
	 * 分页查询 sql全路径名，参数
	 */
	public void selectListByPage(String name, ResultByPage rbp)
			throws Exception {
		if (!rbp.queryFlag())
		{
			return;
		}
		rbp.setSql(getSqlByCodeKey(name, rbp.getCondition()));
		logger.info("列表sql is： {}",rbp.getSql());
		if(rbp.isExportflag())
		{
			rbp.setResultlist(queryBlobForList(rbp.getSql()));
		}else{
			rbp.setResultlist(queryBlobForList(generatorPageSql(rbp)));
		}
		rbp.setAllcount(getCountBySql(rbp.getSql()));
		rbp.setFieldlist(getTableHead(name));
		//logger.debug(rbp.getSql());
		
		//System.out.println(rbp.getSql());
	}
	
	/**
	 * 分页查询 sql全路径名，参数
	 */
	public void selectListByPageSql(String sql, ResultByPage rbp) throws Exception {
		rbp.setSql(sql);
		rbp.setResultlist(queryBlobForList(rbp.getSql()));
		rbp.setAllcount(getCountBySql(rbp.getSql()));
		//logger.debug(rbp.getSql());
		System.out.println(rbp.getSql());
	}
	
	/**
	 * 不分页查询 sql全路径名，参数
	 */
	public void selectList(String name, ResultByPage rbp)
			throws Exception {
		rbp.setSql(getSqlByCodeKey(name, rbp.getCondition()));
		rbp.setResultlist(queryBlobForList(rbp.getSql()));
		rbp.setFieldlist(getTableHead(name));
		//logger.debug(rbp.getSql());
	}
	
	/**
	 * 不分页查询 sql全路径名，参数
	 */
	public void selectListBySql(String sql, ResultByPage rbp)
			throws Exception {
		rbp.setSql(sql);
		rbp.setResultlist(queryBlobForList(rbp.getSql()));
		//logger.debug(rbp.getSql());
	}

	/**
	 * 分页查询 sql全路径名，参数
	 */
	public void selectListByPageOfBlob(String name, ResultByPage rbp)
			throws Exception {
		rbp.setSql(getSqlByCodeKey(name, rbp.getCondition()));
		rbp.setResultlist(queryBlobForList(generatorPageSql(rbp)));
		rbp.setAllcount(getCountBySql(rbp.getSql()));
		rbp.setFieldlist(getTableHead(name));
		//logger.debug(rbp.getSql());
	}

	/**
	 * 执行update，insert语句
	 */
	public void execute(String name, DataRow param) throws Exception {
		long startsql = System.currentTimeMillis();
		String sql = getSqlByCodeKey(name, param);
		jdbcTemplate.execute(sql);
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(sql,sqlcost);
	}

	/**
	 * 分页查询 sql全路径名，参数
	 */
	public void selectListByTestSql(ResultByPage rbp) throws Exception  {
		String sqlcode=  rbp.getCondition().getString("sqlcode").replaceAll("``","#{");
		//logger.debug(sqlcode);
		long startsql = System.currentTimeMillis();
		String sql = generatorSql(sqlcode, rbp.getCondition());
		rbp.setSql(sql);
		
		// 查询
		final ArrayList list = new ArrayList();
		final ArrayList fieldlist = new ArrayList();
		jdbcTemplate.query(generatorPageSql(rbp), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				DataRow data = new DataRow();
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int column = 1; column <= rsmd.getColumnCount(); column++) {
					// logger.debug(rsmd.getColumnName(column)+" type="+rsmd.getColumnType(column));
					if (rsmd.getColumnType(column) == Types.BLOB
							|| rsmd.getColumnType(column) == Types.LONGVARBINARY) {
						try {
							Blob blob = rs.getBlob(column);
							data.put(rsmd.getColumnName(column), (new String(
									blob.getBytes(1, (int) blob.length()),
									"GBK")));
							// logger.debug(rsmd.getColumnName(column)+"="+data.getString(rsmd.getColumnName(column)));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					} else {
						data.put(rsmd.getColumnName(column),
								rs.getObject(column));
					}
					if (fieldlist.size() < rsmd.getColumnCount()) {
						DataRow head = new DataRow();
						head.put("tablename", rsmd.getTableName(column));
						head.put("columnlabel", rsmd.getColumnLabel(column));
						head.put("fieldname", rsmd.getColumnName(column));
						head.put("fieldid", rsmd.getColumnLabel(column));
						head.put("ordernum", column);
						head.put("sortflag", 1);
						fieldlist.add(head);
					}
				}
				list.add(data);
			}
		});
		rbp.setAllcount(getCountBySql(rbp.getSql()));
		rbp.setResultlist(list);
		rbp.setFieldlist(fieldlist);
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(sql,sqlcost,sqlcode);
	}

	/**
	 * 分页查询 sqlRef，参数
	 */
	public void selectListByTestSqlKey(String sqlRef, ResultByPage rbp)
			throws Exception {
		long startsql = System.currentTimeMillis();
		// 查询
		rbp.setSql(getSqlByCodeKey(sqlRef, rbp.getCondition()));
		final ArrayList list = new ArrayList();
		final ArrayList fieldlist = new ArrayList();
		jdbcTemplate.query(generatorPageSql(rbp), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				DataRow data = new DataRow();
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int column = 1; column <= rsmd.getColumnCount(); column++) {
					// logger.debug(rsmd.getColumnName(column)+" type="+rsmd.getColumnType(column));
					if (rsmd.getColumnType(column) == Types.BLOB
							|| rsmd.getColumnType(column) == Types.LONGVARBINARY) {
						try {
							Blob blob = rs.getBlob(column);
							data.put(rsmd.getColumnName(column), (new String(
									blob.getBytes(1, (int) blob.length()),
									"GBK")));
							// logger.debug(rsmd.getColumnName(column)+"="+data.getString(rsmd.getColumnName(column)));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					} else {
						data.put(rsmd.getColumnName(column),
								rs.getObject(column));
					}
					if (fieldlist.size() < rsmd.getColumnCount()) {
						DataRow head = new DataRow();
						head.put("tablename", rsmd.getTableName(column));
						head.put("columnlabel", rsmd.getColumnLabel(column));
						head.put("fieldname", rsmd.getColumnName(column));
						head.put("fieldid", rsmd.getColumnName(column));
						head.put("ordernum", column);
						head.put("sortflag", 1);
						fieldlist.add(head);
					}
				}
				list.add(data);
			}
		});

		rbp.setResultlist(list);
		rbp.setFieldlist(fieldlist);
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(rbp.getSql(),sqlcost);
	}

	/**
	 * @Comments 通过表名和主键查询单条数据
	 */
	public DataRow querySimpleRowByKey(String tablename, String simplekey)
			throws Exception {
		String sql = generatorQuerySqlByKey(tablename, simplekey);
		return querySimpleRowBySql(sql);
	}

	/**
	 * @Comments 通过表名和主键查询单条数据
	 * 
	 * @Param tablename表名 param为查询条件 sortstr为排序字符串
	 */
	public DataRow querySimpleRowByParam(String tablename, DataRow param,
			String sortstr) throws Exception {
		String sql = generatorQuerySqlByParam(tablename, param, sortstr,
				" limit 1");
		//logger.debug(sql);
		return querySimpleRowBySql(sql);
	}

	/**
	 * @Comments 通过表名和条件集合查询数据列表
	 * 
	 * @Param tablename表名 param为查询条件 sortstr为排序字符串 limitstr为查询条数限制
	 */
	public List<DataRow> queryListByParam(String tablename, DataRow param,
			String sortstr, String limitstr) throws Exception {
		String sql = generatorQuerySqlByParam(tablename, param, sortstr,
				limitstr);
		//logger.debug("sql-->"+sql);
		return queryBlobForList(sql);
	}

	/**
	 *  根据SQL的唯一ID获取表头
	 * @param codekey
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> getTableHead(String codekey) throws Exception {
		if (!fieldmap.containsKey(codekey)) {
			String[] keystr = codekey.split("\\.");
			if (keystr.length == 2) {
				DataRow param = new DataRow();
				param.put("codepath", keystr[0]);
				param.put("codeid", keystr[1]);
				param.put("state", 1);
				List<DataRow> headlist = queryListByParam("td_m_tablehead",
						param, "order by ordernum", null);
				if (headlist.size() > 0) {
					//logger.debug(codekey+" size -->"+fieldmap.size());
					fieldmap.put(codekey, headlist);
				}
			}
		}
		return fieldmap.get(codekey);
	}

	/**
	 *  根据SQL的唯一ID移除SQL语句
	 * @param codekey
	 */
	public void removeSqlByCodeKey(String codekey) {
		if (codemap.containsKey(codekey)) {
			codemap.remove(codekey);
		}
	}
	/**
	 *  根据SQL的唯一ID移除SQL语句
	 * @param codekey
	 */
	public void removeTableHeadByCodeKey(String codekey) {
		if (fieldmap.containsKey(codekey)) {
			fieldmap.remove(codekey);
		}
	}

	/**
	 *  根据SQL的唯一ID获取SQL语句
	 * @param codekey
	 * @return
	 * @throws Exception
	 */
	public String getSqlByCodeKey(String codekey) throws Exception {
		//logger.debug("getSqlByCodeKey :" + codekey);
		if (!codemap.containsKey(codekey)) {
			String[] keystr = codekey.split("\\.");
			if (keystr.length == 2) {
				DataRow param = new DataRow();
				param.put("codepath", keystr[0]);
				param.put("codeid", keystr[1]);
				param.put("state", 1);
				DataRow code = querySimpleRowByParam("code_code", param, null);
				if (code != null && !code.isEmpty()) {
					codemap.put(codekey, code.getString("sqlcode"));
				}else {
					logger.error("code_code 中没有找到指定的sql语句！codepath ：{},codeid :{}",keystr[0],keystr[1]);
				}
			}
		}
		if(!codemap.containsKey(codekey))
		{
			logger.debug("语句未找到："+codekey);
		}
		return codemap.get(codekey);
	}

	/**
	 *  根据SQL的唯一ID和查询条件获取SQL语句
	 * @param codekey
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public String getSqlByCodeKey(String codekey, DataRow condition)
			throws Exception {
		return generatorSql(getSqlByCodeKey(codekey), condition);
	}

	/**
	 *  按行替换SQL语句中的变量
	 * @param line
	 * @param condition
	 * @return
	 */
	public String generatorSqlByLine(String line, DataRow condition) {
		Pattern pattern = Pattern.compile("#\\{.*?\\}");
		Matcher matches = pattern.matcher(line);
		while (matches.find()) {
			String mstr = matches.group();
			String keystr = mstr.replace("#{", "").replace("}", "")
					.toUpperCase();
			if (condition != null && condition.containsKey(keystr)
					&& condition.get(keystr) != null
					&& !condition.get(keystr).toString().equals("")) {
				line = line.replace(mstr, condition.get(keystr).toString());
			} else {
				return "";
			}
		}
		return line;
	}

	/**
	 *  替换SQL语句中的变量
	 * @param sql
	 * @param condition
	 * @return
	 */
	public String generatorSql(String sql, DataRow condition) {
		String[] sqls = sql.split("\n");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sqls.length; i++) {
			sb.append(generatorSqlByLine(sqls[i], condition)).append("\n");
		}
		//logger.debug(sb.toString());
		return sb.toString();
	}

	/**
	 *  组织查询结果集条数的SQL
	 * @param rbp
	 * @return
	 
	public String generatorCountSql(ResultByPage rbp) {
		return "select count(1) allcount from ( " + rbp.getSql() + " ) row_";
	}
	*/
	/**
	 * 根据sql获取结果集
	 * @param sql
	 * @throws Exception
	 */
	public int getCountBySql(String sql)
			throws Exception {
		long startsql = System.currentTimeMillis();
		//int count = jdbcTemplate.queryForInt("select found_rows() AS rowcount");
		int count = Integer.valueOf(jdbcTemplate.queryForObject("select found_rows() AS rowcount",Integer.class));
		/**
		int count = jdbcTemplate.query(sql, new ResultSetExtractor() {
			@Override
			public Object extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if(rs.last())
				{
					return rs.getRow();
				}
				return 0;
			}
		});
		**/
		//logger.debug("found_rows :"+count);
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(sql,sqlcost);
		return count;
	}
	/**
	 * 分页查询语句处理
	 * @param rbp
	 * @return
	 */
	public String generatorPageSql(ResultByPage rbp) {
		StringBuilder sql = new StringBuilder();
		sql.append(rbp.getSql());
		
		if (!rbp.getOrderbyfield().isEmpty()) {
			int calcstart = sql.toString().toUpperCase().indexOf("SQL_CALC_FOUND_ROWS");
			if(calcstart>0)
			{
				sql.delete(calcstart, calcstart+19);
			}
			sql.insert(0,"select SQL_CALC_FOUND_ROWS * from ( ").append(" ) order_ ").append("order by ");
			Iterator orderfields = rbp.getOrderbyfield().keySet().iterator();
			while (orderfields.hasNext()) {
				String fieldname = (String) orderfields.next();
				sql.append(fieldname).append(" ")
						.append(rbp.getOrderbyfield().get(fieldname))
						.append(",");
			}
			sql.delete(sql.length() - 1, sql.length());
		}
		sql.append("  limit ").append(rbp.getStart()).append(" , ")
				.append(rbp.getPerpage());
		//logger.debug(sql.toString());
		return sql.toString();
	}

	/**
	 * @Comments 通过主键Insert表数据
	 */
	public int Insert(String tablename, DataRow param) throws Exception {
		long startsql = System.currentTimeMillis();
		String sql = generatorInsertSql(tablename, param);
		int ret =  jdbcTemplate.update(sql);
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(sql, sqlcost);
		return ret;
		
	}

	/**
	 * 通过主键自增的情况  Insert表数据,返回自增主键
	 * @param tablename
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int InsertByIncrement(String tablename, DataRow param) throws Exception{
		long startsql = System.currentTimeMillis();
		String sql = generatorInsertIncrementSql(tablename, param);
		int ret =  jdbcTemplate.update(sql);
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(sql, sqlcost);
		return ret;
	}

	/**
	 * @Comments 通过主键Update表数据
	 */
	public int UpdateByKey(String tablename, DataRow param) throws Exception {
		long startsql = System.currentTimeMillis();
		String sql = generatorUpdateSqlByKey(tablename, param);
		int ret =  jdbcTemplate.update(sql);
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(sql, sqlcost);
		return ret;
	}
	
	public int UpdateBySql(String sql) throws Exception
	{
		long startsql = System.currentTimeMillis();
		int ret = jdbcTemplate.update(sql);
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(sql, sqlcost);
		return ret;
	}

	/**
	 * @Comments 通过参数Upate表数据
	 */
	public int UpdateByParam(String tablename, DataRow param, DataRow codition)
			throws Exception {
		long startsql = System.currentTimeMillis();
		String sql = generatorUpdateSqlByParam(tablename, param, codition);
		int ret =  jdbcTemplate.update(sql);
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(sql, sqlcost);
		return ret;
	}

	/**
	 * @Comments 通过主键Delete表数据
	 */
	public int DelByKey(String tablename, DataRow param) throws Exception {
		long startsql = System.currentTimeMillis();
		String sql = generatorDeleteSqlByKey(tablename, param);
		int ret =  jdbcTemplate.update(sql);
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(sql, sqlcost);
		return ret;
	}

	/**
	 * @Comments 通过参数Delete表数据
	 */
	public int DelByParam(String tablename, DataRow param) throws Exception {
		long startsql = System.currentTimeMillis();
		String sql = generatorDeleteSqlByParam(tablename, param);
		int ret =  jdbcTemplate.update(sql);
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(sql, sqlcost);
		return ret;
	}

	/**
	 * @Comments 通过参数自动生成Insert语句
	 */

	// 自动生成Insert语句
	public String generatorInsertSql(String tablename, DataRow param)
			throws Exception {
		List<DataRow> columnslist;
		if (tablefield.containsKey(tablename)) {
			columnslist = tablefield.get(tablename);
		} else {
			// 查询表字段
			columnslist = getTableColumnList(tablename);
			if (columnslist != null) {
				tablefield.put(tablename, columnslist);
			}
		}
		if(columnslist.size()==0)
		{
			throw new Exception("操作的表："+tablename+"不存在");
		}
		StringBuilder insertstr = new StringBuilder();
		StringBuilder insertvaluestr = new StringBuilder();
		insertstr.append("\t\t").append("insert into ").append(tablename)
				.append("(\n");
		insertvaluestr.append("\t\t").append("values (\n");
		for (DataRow item : columnslist) {
			if (item.containsKey("column_key")
					&& item.get("column_key").toString().equals("PRI")) {
				if (!param.containsKey(item.get("column_name").toString()) && !item.get("column_extra").toString().equals("auto_increment")) {
					throw new Exception("主键不能为空");
				}
			}

			if ((param.containsKey(item.getString("column_name"))
					&&(param.get(item.getString("column_name"))!=null
					&&!param.getString(item.getString("column_name")).isEmpty()))
					|| (item.containsKey("column_default")
							&& item.get("column_default") != null &&! item
							.getString("column_default").isEmpty())) {
				insertstr.append("\t\t\t").append(item.get("column_name"))
						.append(",\n");
				if (param.containsKey(item.getString("column_name"))
						&&param.get(item.getString("column_name"))!=null
						&&!param.getString(item.getString("column_name")).isEmpty()) {
					if (item.get("data_type").equals("int")) {
						insertvaluestr.append(
								param.getString(item.get("column_name")
										.toString())).append(",\n");
					} else {
						insertvaluestr
						.append("'")
						.append(param.getString(
								item.get("column_name").toString())
								.replaceAll("\\'", "\\\\'"))
						.append("',\n");
						/**
						if(param.getString(item.get("column_name").toString()).indexOf("\\")>0)
						{
							insertvaluestr
							.append("'")
							.append(param.getString(
									item.get("column_name").toString()))
							.append("',\n");
						}else{
							insertvaluestr
							.append("'")
							.append(param.getString(
									item.get("column_name").toString()))
							.append("',\n");
						}
						**/
					}
				} else {
					if (item.get("data_type").equals("int")) {
						insertvaluestr.append(item.get("column_default"))
								.append(",\n");
					} else {
						insertvaluestr.append("'")
								.append(item.get("column_default"))
								.append("',\n");
					}
				}
			}

		}
		insertstr.delete(insertstr.lastIndexOf(","), insertstr.length());
		insertvaluestr.delete(insertvaluestr.lastIndexOf(","),
				insertvaluestr.length());
		insertstr.append(")\n");
		insertvaluestr.append(")\n");
		insertstr.append(insertvaluestr);
		return insertstr.toString();
	}

	// 自动生成Insert语句
	public String generatorInsertIncrementSql(String tablename, DataRow param)
			throws Exception {
		List<DataRow> columnslist;
		if (tablefield.containsKey(tablename)) {
			columnslist = tablefield.get(tablename);
		} else {
			// 查询表字段
			columnslist = getTableColumnList(tablename);
			if (columnslist != null) {
				tablefield.put(tablename, columnslist);
			}
		}
		if(columnslist.size()==0)
		{
			throw new Exception("操作的表："+tablename+"不存在");
		}
		StringBuilder insertstr = new StringBuilder();
		StringBuilder insertvaluestr = new StringBuilder();
		insertstr.append("\t\t").append("insert into ").append(tablename)
				.append("(\n");
		insertvaluestr.append("\t\t").append("values (\n");
		for (DataRow item : columnslist) {
			if (item.containsKey("column_key")
					&& item.get("column_key").toString().equals("PRI")) {
				if (!param.containsKey(item.get("column_name").toString()) && !item.get("column_extra").toString().equals("auto_increment")) {
					throw new Exception("主键不能为空");
				}
			}

			if ((param.containsKey(item.getString("column_name"))
					&&(param.get(item.getString("column_name"))!=null
					&&!param.getString(item.getString("column_name")).isEmpty()))
					|| (item.containsKey("column_default")
					&& item.get("column_default") != null &&! item
					.getString("column_default").isEmpty())) {
				insertstr.append("\t\t\t").append(item.get("column_name"))
						.append(",\n");
				if (param.containsKey(item.getString("column_name"))
						&&param.get(item.getString("column_name"))!=null
						&&!param.getString(item.getString("column_name")).isEmpty()) {
					if (item.get("data_type").equals("int")) {
						insertvaluestr.append(
								param.getString(item.get("column_name")
										.toString())).append(",\n");
					} else {
						insertvaluestr
								.append("'")
								.append(param.getString(
										item.get("column_name").toString())
										.replaceAll("\\'", "\\\\'"))
								.append("',\n");
						/**
						 if(param.getString(item.get("column_name").toString()).indexOf("\\")>0)
						 {
						 insertvaluestr
						 .append("'")
						 .append(param.getString(
						 item.get("column_name").toString()))
						 .append("',\n");
						 }else{
						 insertvaluestr
						 .append("'")
						 .append(param.getString(
						 item.get("column_name").toString()))
						 .append("',\n");
						 }
						 **/
					}
				} else {
					if (item.get("data_type").equals("int")) {
						insertvaluestr.append(item.get("column_default"))
								.append(",\n");
					} else {
						insertvaluestr.append("'")
								.append(item.get("column_default"))
								.append("',\n");
					}
				}
			}

		}
		insertstr.delete(insertstr.lastIndexOf(","), insertstr.length());
		insertvaluestr.delete(insertvaluestr.lastIndexOf(","),
				insertvaluestr.length());
		insertstr.append(")\n");
		insertvaluestr.append(")\n");
		insertstr.append(insertvaluestr);
		return insertstr.toString();
	}
	/**
	 *  查询表字段内容
	 * @param tablename
	 * @param param
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> getTableColumnList(String tablename) {
		//String sql = "select column_name,data_type,column_comment,column_key,column_default from information_schema.columns where table_name='"
		//+ tablename + "' order by ORDINAL_POSITION";
		String sql = "DESC "+tablename;
		final List<DataRow> columnlist = new ArrayList<DataRow>();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				DataRow data = new DataRow();
				data.put("column_name", rs.getString("field"));
				if(rs.getString("type").indexOf("(")>0)
				{
					data.put("data_type", rs.getString("type").substring(0,rs.getString("type").indexOf("(")));
				}else{
					data.put("data_type", rs.getString("type"));
				}
				data.put("column_comment","");
				data.put("column_key", rs.getString("key"));
				data.put("column_default", rs.getString("default"));
				data.put("column_extra",rs.getString("extra"));
				columnlist.add(data);
			}
		});
		return columnlist;
	}

	/**
	 *  自动生成按主键Update语句
	 * @param tablename
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String generatorUpdateSqlByKey(String tablename, DataRow param)
			throws Exception {
		List<DataRow> columnslist;
		if (tablefield.containsKey(tablename)) {
			columnslist = tablefield.get(tablename);
		} else {
			// 查询表字段
			columnslist = getTableColumnList(tablename);
			if (columnslist != null) {
				tablefield.put(tablename, columnslist);
			}
		}
		StringBuilder updatestr = new StringBuilder();
		StringBuilder updatewherestr = new StringBuilder();
		updatestr.append("\t\t").append("update ").append(tablename)
				.append("\n").append(" \t\tset\n");
		for (DataRow item : columnslist) {
			if (item.containsKey("column_key")
					&& item.get("column_key").toString().equals("PRI")) {
				if (!param.containsKey(item.get("column_name").toString())) {
					throw new Exception("主键不能为空");
				}
				
				if (!param.containsKey(item.getString("column_name"))) {
					continue;
				}
				
				if (updatewherestr.length() > 0) {
					if (item.get("data_type").equals("int")) {
						updatewherestr
								.append("\t\t and ")
								.append(item.get("column_name"))
								.append("=")
								.append(param.getString(item.get("column_name")
										.toString())).append(" \n");
					} else {
						updatewherestr
								.append("\t\t and ")
								.append(item.get("column_name"))
								.append("='")
								.append(param.getString(
										item.get("column_name").toString())
										.replaceAll("\\'", "\\\\'"))
								.append("' \n");
					}
				} else {
					if (item.get("data_type").equals("int")) {
						updatewherestr
								.append("\t\t")
								.append(item.get("column_name"))
								.append("=")
								.append(param.getString(item.get("column_name")
										.toString())).append(" \n");
					}else {
						updatewherestr
								.append("\t\t")
								.append(item.get("column_name"))
								.append("='")
								.append(param.getString(
										item.get("column_name").toString())
										.replaceAll("\\'", "\\\\'"))
								.append("' \n");
					}
				}

			} else {
				if (!param.containsKey(item.getString("column_name"))
						||param.get(item.getString("column_name"))==null
						||param.getString(item.getString("column_name")).isEmpty()) {
					if(!param.containsKey(item.getString("column_name")))
					{
						continue;
					}else if(param.get(item.getString("column_name"))==null){
						updatestr
						.append("\t\t")
						.append(item.get("column_name"))
						.append(" = null, \n");
					}else if(param.getString(item.getString("column_name")).isEmpty()){
						updatestr
						.append("\t\t")
						.append(item.get("column_name"))
						.append(" = '', \n");
					}
				}else if (item.get("data_type").equals("int")) {
					updatestr
							.append("\t\t")
							.append(item.get("column_name"))
							.append("=")
							.append(param.getString(item.get("column_name")
									.toString())).append(", \n");
				}else {
					if (param.containsKey(item.get("column_name").toString())) {
						if(param.getString(item.get("column_name").toString())==null)
						{
							updatestr
							.append("\t\t")
							.append(item.get("column_name"))
							.append("='")
							.append(param.getString(
									item.get("column_name").toString()))
							.append("', \n");
						}else{
						updatestr
								.append("\t\t")
								.append(item.get("column_name"))
								.append("='")
								.append(param.getString(
										item.get("column_name").toString())
										.replaceAll("\\'", "\\\\'"))
								.append("', \n");
						}
					}
				}
			}
		}
		updatestr.delete(updatestr.lastIndexOf(","), updatestr.length());
		updatestr.append(" \n \t\t where ").append(updatewherestr);

		return updatestr.toString();
	}

	/**
	 * 按参数自动生成Update语句
	 * 
	 * @param 表名，更新参数集合，条件参数集合
	 */
	public String generatorUpdateSqlByParam(String tablename, DataRow param,
			DataRow condition) throws Exception {
		List<DataRow> columnslist;
		if (tablefield.containsKey(tablename)) {
			columnslist = tablefield.get(tablename);
		} else {
			// 查询表字段
			columnslist = getTableColumnList(tablename);
			if (columnslist != null) {
				tablefield.put(tablename, columnslist);
			}
		}
		StringBuilder updatestr = new StringBuilder();
		StringBuilder updatewherestr = new StringBuilder();
		updatestr.append("\t\t").append("update ").append(tablename)
				.append("\n").append(" \t\tset\n");
		for (DataRow item : columnslist) {
			if (!param.containsKey(item.getString("column_name"))
					&& !condition.containsKey(item.getString("column_name"))) {
				continue;
			}
			if (condition.containsKey(item.getString("column_name"))) {
				if (updatewherestr.length() > 0) {
					if (item.get("data_type").equals("int")||item.get("data_type").equals("decimal")) {
						updatewherestr
								.append("\t\t and ")
								.append(item.get("column_name"))
								.append("=")
								.append(condition.getString(item.get(
										"column_name").toString()))
								.append(" \n");
					} else {
						if(condition.getString(item.get("column_name").toString())==null)
						{
							updatewherestr
							.append("\t\t and ")
							.append(item.get("column_name"))
							.append(" is null \n");
						}else{
							updatewherestr
								.append("\t\t and ")
								.append(item.get("column_name"))
								.append("='")
								.append(condition.getString(
										item.get("column_name").toString())
										.replaceAll("\\'", "\\\\'"))
								.append("' \n");
						}
					}
				} else {
					if (item.get("data_type").equals("int")||item.get("data_type").equals("decimal")) {
						updatewherestr
								.append("\t\t")
								.append(item.get("column_name"))
								.append("=")
								.append(condition.getString(item.get(
										"column_name").toString()))
								.append(" \n");
					} else {
						if(condition.getString(item.get("column_name").toString())==null)
						{
							updatewherestr
							.append("\t\t ")
							.append(item.get("column_name"))
							.append(" is null \n");
						}else{
							updatewherestr
								.append("\t\t")
								.append(item.get("column_name"))
								.append("='")
								.append(condition.getString(
										item.get("column_name").toString())
										.replaceAll("\\'", "\\\\'"))
								.append("' \n");
						}
					}
				}

			}
			if (param.containsKey(item.getString("column_name"))) {
				if (item.get("data_type").equals("int")||item.get("data_type").equals("decimal")) {
					if(!param.getString(item.get("column_name")
									.toString(),"").isEmpty())
					{
						updatestr
							.append("\t\t")
							.append(item.get("column_name"))
							.append("=")
							.append(param.getString(item.get("column_name")
									.toString(),"")).append(", \n");
					}
				} else {
					if (param.containsKey(item.get("column_name").toString())) {
						updatestr
								.append("\t\t")
								.append(item.get("column_name"))
								.append("='")
								.append(param.getString(
										item.get("column_name").toString(),"")
										.replaceAll("\\'", "\\\\'"))
								.append("', \n");
					}
				}
			}
		}
		updatestr.delete(updatestr.lastIndexOf(","), updatestr.length());
		updatestr.append(" \n \t\t  where ").append(updatewherestr);

		return updatestr.toString();
	}

	/**
	 * 按参数自动生成Delete语句
	 * 
	 * @param 表名，条件参数集合
	 */

	// 自动生成按主键Delete语句
	public String generatorDeleteSqlByKey(String tablename, DataRow param)
			throws Exception {
		List<DataRow> columnslist;
		if (tablefield.containsKey(tablename)) {
			columnslist = tablefield.get(tablename);
		} else {
			// 查询表字段
			columnslist = getTableColumnList(tablename);
			if (columnslist != null) {
				tablefield.put(tablename, columnslist);
			}
		}
		StringBuilder delstr = new StringBuilder();
		StringBuilder updatewherestr = new StringBuilder();

		for (DataRow item : columnslist) {
			//logger.debug(item.toString());
			if (item.containsKey("column_key")
					&& item.get("column_key").toString().equals("PRI")) {
				if (!param.containsKey(item.get("column_name").toString())) {
					throw new Exception("主键不能为空");
				}
				
				if (!param.containsKey(item.getString("column_name"))) {
					continue;
				}
				
				if (updatewherestr.length() > 0) {
					if (item.get("data_type").equals("int")) {
						updatewherestr
								.append("\t\t and ")
								.append(item.get("column_name"))
								.append("=")
								.append(param.getString(item.get("column_name")
										.toString())).append(" \n");
					} else {
						updatewherestr
								.append("\t\t and ")
								.append(item.get("column_name"))
								.append("='")
								.append(param.getString(
										item.get("column_name").toString())
										.replaceAll("\\'", "\\\\'"))
								.append("' \n");
					}
				} else {
					if (item.get("data_type").equals("int")) {
						updatewherestr
								.append("\t\t")
								.append(item.get("column_name"))
								.append("=")
								.append(param.getString(item.get("column_name")
										.toString())).append(" \n");
					} else {
						updatewherestr
								.append("\t\t")
								.append(item.get("column_name"))
								.append("='")
								.append(param.getString(
										item.get("column_name").toString())
										.replaceAll("\\'", "\\\\'"))
								.append("' \n");
					}
				}

			}
		}
		delstr.append("\t\t delete from  ").append(tablename).append("\n")
				.append("\t\t where ").append(updatewherestr);
		return delstr.toString();
	}

	/**
	 * 根据参数自动生成Delete语句
	 * @param tablename
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String generatorDeleteSqlByParam(String tablename, DataRow param)
			throws Exception {
		List<DataRow> columnslist;
		if (tablefield.containsKey(tablename)) {
			columnslist = tablefield.get(tablename);
		} else {
			// 查询表字段
			columnslist = getTableColumnList(tablename);
			if (columnslist != null) {
				tablefield.put(tablename, columnslist);
			}
		}
		StringBuilder delstr = new StringBuilder();
		StringBuilder updatewherestr = new StringBuilder();

		for (DataRow item : columnslist) {
			if (!param.containsKey(item.getString("column_name"))) {
				continue;
			}
			if (updatewherestr.length() > 0) {
				if (item.get("data_type").equals("int")) {
					updatewherestr
							.append("\t\t and ")
							.append(item.get("column_name"))
							.append("=")
							.append(param.getString(item.get("column_name")
									.toString())).append(" \n");
				} else {
					updatewherestr
							.append("\t\t and ")
							.append(item.get("column_name"))
							.append("='")
							.append(param.getString(
									item.get("column_name").toString())
									.replaceAll("\\'", "\\\\'")).append("' \n");
				}
			} else {
				if (item.get("data_type").equals("int")) {
					updatewherestr
							.append("\t\t")
							.append(item.get("column_name"))
							.append("=")
							.append(param.getString(item.get("column_name")
									.toString())).append(" \n");
				} else {
					//logger.debug(item.getString("column_name"));
					//logger.debug(param.toString());
					//logger.debug(param.getString(item.getString("column_name")));
					updatewherestr
							.append("\t\t")
							.append(item.get("column_name"))
							.append("='")
							.append(param.getString(
									item.getString("column_name"))
									.replaceAll("\\'", "\\\\'")).append("' \n");
				}
			}
		}
		delstr.append("\t\t delete from  ").append(tablename).append("\n")
				.append("\t\t where ").append(updatewherestr);
		return delstr.toString();
	}

	/**
	 *  根据参数自动生成查询语句
	 * @param tablename
	 * @param param
	 * @param sortstr
	 * @param limitstr
	 * @return
	 * @throws Exception
	 */
	public String generatorQuerySqlByParam(String tablename, DataRow param,
			String sortstr, String limitstr) throws Exception {
		List<DataRow> columnslist;
		if (tablefield.containsKey(tablename)) {
			columnslist = tablefield.get(tablename);
		} else {
			// 查询表字段
			columnslist = getTableColumnList(tablename);
			if (columnslist != null) {
				tablefield.put(tablename, columnslist);
			}
		}
		StringBuilder querystr = new StringBuilder();
		StringBuilder updatewherestr = new StringBuilder();

		for (DataRow item : columnslist) {
			if (!param.containsKey(item.getString("column_name"))) {
				continue;
			}

			if (updatewherestr.length() > 0) {
				if (item.get("data_type").equals("int")) {
					updatewherestr
							.append("\t\t and ")
							.append(item.get("column_name"))
							.append("=")
							.append(param.getString(item.get("column_name")
									.toString())).append(" \n");
				} else {
					updatewherestr
							.append("\t\t and ")
							.append(item.get("column_name"))
							.append("='")
							.append(param.getString(
									item.get("column_name").toString())
									.replaceAll("\\'", "\\\\'")).append("' \n");
				}
			} else {
				if (item.get("data_type").equals("int")) {
					updatewherestr
							.append("\t\t")
							.append(item.get("column_name"))
							.append("=")
							.append(param.getString(item.get("column_name")
									.toString())).append(" \n");
				} else {
					updatewherestr
							.append("\t\t")
							.append(item.get("column_name"))
							.append("='")
							.append(param.getString(
									item.get("column_name").toString())
									.replaceAll("\\'", "\\\\'")).append("' \n");
				}
			}
		}
		if(param==null||param.isEmpty())
		{
			querystr.append("\t\t select * from  ").append(tablename);
		}else{
			querystr.append("\t\t select * from  ").append(tablename).append("\n")
			.append("\t\t where ").append(updatewherestr);
		}
		if (sortstr != null) {
			querystr.insert(0, "select * from ( ").append(" ) order_ ").append(sortstr);
		}
		if (limitstr != null) {
			querystr.insert(0, "select * from ( ").append(" ) limit_ ").append(limitstr);
		}
		return querystr.toString();
	}

	/**
	 *  根据参数自动生成查询语句
	 * @param tablename
	 * @param simplekey
	 * @return
	 * @throws Exception
	 */
	public String generatorQuerySqlByKey(String tablename, String simplekey)
			throws Exception {
		List<DataRow> columnslist;
		if (tablefield.containsKey(tablename)) {
			columnslist = tablefield.get(tablename);
		} else {
			// 查询表字段
			columnslist = getTableColumnList(tablename);
			if (columnslist != null) {
				tablefield.put(tablename, columnslist);
			}
		}
		StringBuilder querystr = new StringBuilder();
		StringBuilder updatewherestr = new StringBuilder();
	//	logger.debug("columnslist:"+columnslist.size());
		for (DataRow item : columnslist) {
			if (item.containsKey("column_key")
					&& item.get("column_key").toString().equals("PRI")) {

				if (item.get("data_type").equals("int")) {
					updatewherestr.append("\t\t ")
							.append(item.get("column_name")).append("=")
							.append(simplekey).append(" \n");
				} else {
					updatewherestr.append("\t\t ")
							.append(item.get("column_name")).append("='")
							.append(simplekey.replaceAll("\\'", "\\\\'"))
							.append("' \n");
				}

			}
		}
		querystr.append("\t\t select *  from  ").append(tablename).append("\n")
				.append("\t\t where ").append(updatewherestr);
		return querystr.toString();
	}
	/**
	 * 根据工单类型查询对应的处理函数名
	 * @param tradetype
	 * @return
	 * @throws Exception
	 */
	public String getFunctionNameByTradeType(String tradetype) throws Exception
	{
		if(tradebpm.containsKey(tradetype))
		{
			return tradebpm.getString(tradetype);
		}else{
			DataRow param = new DataRow();
			param.put("tradetype", tradetype);
			DataRow item = querySimpleRowByName("bpm_dispatch.selectFunctionByTradetype", param);
			if(item!=null&&!item.isEmpty())
			{
				tradebpm.put(item.getString("bpmtype"), item.getString("funname"));
				return tradebpm.getString(tradetype);
			}else{
				return null;
			}
		}
	}
	/**
	 * 根据主工单内容调用处理函数进行工单处理
	 * @param tradeinfo
	 * @return
	 * @throws Exception
	 */
	public String callTradeServiceFunction(final DataRow tradeinfo) throws Exception
	{
		long startsql = System.currentTimeMillis();
		String functionname="";
		if(tradeinfo.containsKey("tradetype"))
		{
			functionname = getFunctionNameByTradeType(tradeinfo.getString("tradetype"));
		}
		if(functionname!=null&&!functionname.isEmpty())
		{
			String selSql="{? = call "+functionname+"(?,?,?)}";
			String resultstr = jdbcTemplate.execute(selSql, new CallableStatementCallback<String>() {
	             public String doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
	            	 cs.registerOutParameter(1, java.sql.Types.VARCHAR);
	            	 cs.setString(2, tradeinfo.getString("tradeid"));
	            	 cs.setString(3, tradeinfo.getString("orderid"));
	            	 cs.setString(4, tradeinfo.getString("tradetype"));
	                 cs.execute();
	                 return cs.getString(1);
	             }
			});
			long sqlcost = System.currentTimeMillis()-startsql;
			sendSqlLog(selSql, sqlcost);
			return resultstr;
		}else{
			throw new Exception("此工单类型无对应处理函数，TradeType："+tradeinfo.getString("tradetype"));
		}
	}
	
	/**
	 * 无返回值的存储过程调用
	 * @param name 存储过程名
	 */
	public void callFunction(String name)
	{
		long startsql = System.currentTimeMillis();
		jdbcTemplate.execute("{?=call "+name+"}");
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog("{?=call "+name+"}", sqlcost);
	}
	
	/**
	 * 根据表名和条件组织查询条件SQL
	 * @param tablename
	 * @param param
	 * @return
	 */
	public String generatorQuerySqlByParam(String tablename, DataRow param)
	{
		List<DataRow> columnslist;
		if (tablefield.containsKey(tablename)) {
			columnslist = tablefield.get(tablename);
		} else {
			// 查询表字段
			columnslist = getTableColumnList(tablename);
			if (columnslist != null) {
				tablefield.put(tablename, columnslist);
			}
		}
		StringBuilder updatewherestr = new StringBuilder();
		for (DataRow item : columnslist) {
				if (!param.containsKey(item.getString("column_name"))) {
					continue;
				}
				if (updatewherestr.length() > 0) {
					if (item.get("data_type").equals("int")) {
						updatewherestr
								.append("\t\t and ")
								.append(item.get("column_name"))
								.append("=")
								.append(param.getString(item.get("column_name")
										.toString())).append(" \n");
					} else {
						updatewherestr
								.append("\t\t and ")
								.append(item.get("column_name"))
								.append("='")
								.append(param.getString(
										item.get("column_name").toString())
										.replaceAll("\\'", "\\\\'"))
								.append("' \n");
					}
				} else {
					if (item.get("data_type").equals("int")) {
						updatewherestr
								.append("\t\t")
								.append(item.get("column_name"))
								.append("=")
								.append(param.getString(item.get("column_name")
										.toString())).append(" \n");
					}else {
						updatewherestr
								.append("\t\t")
								.append(item.get("column_name"))
								.append("='")
								.append(param.getString(
										item.get("column_name").toString())
										.replaceAll("\\'", "\\\\'"))
								.append("' \n");
					}
				}
		}
		if(updatewherestr.length()<=0)
		{
			return " where 1=1";
		}else{
			updatewherestr.insert(0, " where ");
			return updatewherestr.toString();
		}
	}
	/**
	 * 根据表名和条件查询结果集数量
	 * @param tablename
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int countByParam(String tablename, DataRow param) throws Exception {
		String sql =" select count(1)  nums from "+tablename+generatorQuerySqlByParam(tablename,param);
		DataRow res = querySimpleRowBySql(sql);
		return res.getInt("nums");
	}
	
	/**
	 * 根据表名生成批量语句与批量参数顺序
	 * @param tablename
	 * @param column
	 * @return
	 * @throws Exception
	 */
	public String generatorsqlByBatch(String tablename,List<DataRow> columns) throws Exception
	{
		List<DataRow> columnslist;
		if (tablefield.containsKey(tablename)) {
			columnslist = tablefield.get(tablename);
		} else {
			// 查询表字段
			columnslist = getTableColumnList(tablename);
			if (columnslist != null) {
				tablefield.put(tablename, columnslist);
			}
		}
		if(columnslist.size()==0)
		{
			throw new Exception("操作的表："+tablename+"不存在");
		}
		StringBuilder insertstr = new StringBuilder();
		StringBuilder insertvaluestr = new StringBuilder();
		insertstr.append("\t\t").append("insert into ").append(tablename)
				.append("(\n");
		insertvaluestr.append("\t\t").append("values (\n");
		for (DataRow item : columnslist) {
			insertstr.append("\t\t\t").append(item.get("column_name")).append(",\n");
			insertvaluestr.append("?,");
			columns.add(item);
		}
		insertstr.delete(insertstr.lastIndexOf(","), insertstr.length());
		insertvaluestr.delete(insertvaluestr.lastIndexOf(","),
				insertvaluestr.length());
		insertstr.append(")\n");
		insertvaluestr.append(")\n");
		insertstr.append(insertvaluestr);
		return insertstr.toString();
	}
	/**
	 * 批量执行插入语句
	 * @param sql
	 * @param list
	 * @param columns
	 * @throws Exception 
	 */
	public void batchInsert(String tablename ,List<DataRow> paramlist) throws Exception
	{
		long startsql = System.currentTimeMillis();
		final List<DataRow> list = paramlist;
		List<DataRow> columns =new ArrayList<DataRow>();
		String sql = generatorsqlByBatch(tablename,columns);
		final List<DataRow> columnlist = columns;
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			 public int getBatchSize()
            {
				return list.size();
            }
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				DataRow item = list.get(i);
				for(int ci=0;ci<columnlist.size();ci++)
				{
					String column_name = columnlist.get(ci).getString("column_name");
					if(item.getString(column_name)==null)
					{
						//logger.debug((ci+1)+"--set--"+columnlist.get(ci).getString("column_default"));
						ps.setString(ci+1,columnlist.get(ci).getString("column_default"));
						
					}else{
						//logger.debug((ci+1)+"--set--"+item.getString(column_name));
						ps.setString(ci+1, item.getString(column_name));
						
					}
				}
			}
		});
		long sqlcost = System.currentTimeMillis()-startsql;
		sendSqlLog(sql, sqlcost);
	}
	public ArrayList querySimpleColumnBySql(String sql)
	{
		final ArrayList list  = new ArrayList();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				list.add(rs.getObject(1));
			}
		});
		return list;
	}
	
	public static boolean isSqllogflag()
	{
		return sqllogflag;
	}

	public static void setSqllogflag(boolean sqllogflag)
	{
		DBService.sqllogflag = sqllogflag;
	}
	
	public static boolean isStackTraceFlag()
	{
		return stackTraceFlag;
	}

	public static void setStackTraceFlag(boolean stackTraceFlag)
	{
		DBService.stackTraceFlag = stackTraceFlag;
	}

	public static long getCosttime()
	{
		return costtime;
	}

	public static void setCosttime(long costtime)
	{
		DBService.costtime = costtime;
	}
	
	/**
	 * 记录日志
	 * @param sql  语句
	 * @param sqlcost 耗时
	 */
	public void sendSqlLog(String sql,long sqlcost)
	{
		if(DBService.isSqllogflag())
		{
			if(sqlcost>DBService.getCosttime())
			{
				long startsql = System.currentTimeMillis();
				logger.debug(startsql+"--->"+sql);
				logger.debug(startsql+"-->sql cost-->"+sqlcost);
				if(DBService.isStackTraceFlag()&&sqlcost>1000)//打印堆栈信息
				{
					//SysManageService.setStackTraceFlag(false);//关闭打印堆栈信息的开关，避免重复打印
					StackTraceElement[] stackElements = Thread.currentThread().getStackTrace();
					logger.debug("----------------stackElements start-------------------");
					if (stackElements != null) {
			            for (int i = 0; i < stackElements.length; i++) {
			            	String logstr = stackElements[i].getClassName()+"\t"
			            			+stackElements[i].getFileName()+",\t"
			            			+stackElements[i].getLineNumber()+",\t"
			            			+stackElements[i].getMethodName();
			            	logger.debug(logstr);
			            }
			        }
					logger.debug("----------------stackElements end-------------------");
				}
			}
		}
	}
	/**
	 * 记录日志
	 * @param sql  语句
	 * @param sqlcost 耗时
	 * @param sqlcode sql名称
	 */
	public void sendSqlLog(String sql,long sqlcost,String sqlcode)
	{
		if(DBService.isSqllogflag())
		{
			if(sqlcost>DBService.getCosttime())
			{
				if(sqlcode!=null)
				{
					logger.debug(sqlcode+"--->"+sqlcode);
				}
				sendSqlLog(sql,sqlcost);
			}
		}
	}
}
