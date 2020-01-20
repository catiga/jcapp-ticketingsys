package com.jeancoder.ticketingsys.ready.film

import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.stream.Collectors
import java.util.zip.GZIPInputStream

import org.slf4j.Logger

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.MemSource
import com.jeancoder.core.log.JCLogger
import com.piaodaren.ssc.model.CinemaPlanResult

class MaoyanHelper {
	static JCLogger logger = LoggerSource.getLogger();
	
	public static final MaoyanHelper INSTANCE = new MaoyanHelper();
	
	private String comming_pre = "comming@";
	//private Long commonExp = 1000L*60L*30L;
	private Long commonExp = 5*24*60*60L;	//5天缓存
	
	public String getSnapshot(String movieName) {
		String useName = movieName;
		useName=useName.replaceAll("（.*）", "");
		useName=useName.replaceAll(" .*\$", "");
		useName = URLEncoder.encode(useName,"utf-8");
		URL url = new URL("http://m.maoyan.com/ajax/search?kw="+useName+"&cityId=1&stype=-1");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		
		conn.setRequestProperty("Accept"," text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		conn.setRequestProperty("Accept-Encoding"," gzip, deflate");
		conn.setRequestProperty("Accept-Language"," zh-CN,zh;q=0.9,en;q=0.8");
		conn.setRequestProperty("Cache-Control"," max-age=0");
		conn.setRequestProperty("Connection"," keep-alive");
		conn.setRequestProperty("Host"," m.maoyan.com");
		conn.setRequestProperty("Upgrade-Insecure-Requests"," 1");
		conn.setRequestProperty("User-Agent"," Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
		
		String result = null;
		boolean isGzip = false;
		String hfield = null;
		int i=0;
		while((hfield = conn.getHeaderField(i++)) != null) {
			if("Content-Encoding".equals(conn.getHeaderFieldKey(i-1))) {
				if(hfield.indexOf("gzip") != -1 || hfield.indexOf("GZIP") != -1) {
					isGzip = true;
					break;
				}
			}
		}
		if(isGzip) {
			result = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream()),"utf-8"))
			.lines().collect(Collectors.joining(System.lineSeparator()));
		}else {
			result = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"))
					.lines().collect(Collectors.joining(System.lineSeparator()));
		}
		
		conn.disconnect();
		return result;
	}
	
	public String getMovieHtml(String maoyanId) {
		URL url = new URL("http://m.maoyan.com/movie/"+maoyanId+"?_v_=yes&channelId=4&cityId=1&\$from=canary#");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		
		conn.setRequestProperty("Accept"," text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		conn.setRequestProperty("Accept-Encoding"," gzip, deflate");
		conn.setRequestProperty("Accept-Language"," zh-CN,zh;q=0.9,en;q=0.8");
		conn.setRequestProperty("Cache-Control"," max-age=0");
		conn.setRequestProperty("Connection"," keep-alive");
		conn.setRequestProperty("Host"," m.maoyan.com");
		conn.setRequestProperty("Upgrade-Insecure-Requests"," 1");
		conn.setRequestProperty("User-Agent"," Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
		
		String result = null;
		boolean isGzip = false;
		String hfield = null;
		int i=0;
		while((hfield = conn.getHeaderField(i++)) != null) {
			if("Content-Encoding".equals(conn.getHeaderFieldKey(i-1))) {
				if(hfield.indexOf("gzip") != -1 || hfield.indexOf("GZIP") != -1) {
					isGzip = true;
					break;
				}
			}
		}
		if(isGzip) {
			result = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream()),"utf-8"))
			.lines().collect(Collectors.joining(System.lineSeparator()));
		}else {
			result = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"))
					.lines().collect(Collectors.joining(System.lineSeparator()));
		}
		
		conn.disconnect();
		return result;
	}
	
	public String getComming() {
		String cacheKey = comming_pre;
		Object cacheObj = MemSource.getMemPower().get(cacheKey);
		if(cacheObj != null && cacheObj.toString().trim()!='') {
			//return (String)cacheObj;
			//清除缓存
			//MemSource.getMemPower().delete(cacheKey);
			logger.info('cacheKey===' + cacheKey + ' aimed comming movie list.');
			return cacheObj;
		}
		
		
		URL url = new URL("http://m.maoyan.com/ajax/comingList?ci=1&token=&limit=50");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		
		conn.setRequestProperty("Accept"," text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		conn.setRequestProperty("Accept-Encoding"," gzip, deflate");
		conn.setRequestProperty("Accept-Language"," zh-CN,zh;q=0.9,en;q=0.8");
		conn.setRequestProperty("Cache-Control"," max-age=0");
		conn.setRequestProperty("Connection"," keep-alive");
		conn.setRequestProperty("Host"," m.maoyan.com");
		conn.setRequestProperty("Upgrade-Insecure-Requests"," 1");
		conn.setRequestProperty("User-Agent"," Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
		
		String result = null;
		boolean isGzip = false;
		String hfield = null;
		int i=0;
		while((hfield = conn.getHeaderField(i++)) != null) {
			if("Content-Encoding".equals(conn.getHeaderFieldKey(i-1))) {
				if(hfield.indexOf("gzip") != -1 || hfield.indexOf("GZIP") != -1) {
					isGzip = true;
					break;
				}
			}
		}
		if(isGzip) {
			result = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream()),"utf-8"))
			.lines().collect(Collectors.joining(System.lineSeparator()));
		}else {
			result = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"))
					.lines().collect(Collectors.joining(System.lineSeparator()));
		}
		
		conn.disconnect();
		//cache option
		result = result.replaceAll("w\\.h", "180.250")
		MemSource.getMemPower().setUntil(cacheKey, result, commonExp);
		//cache option
		return result;
		
	}
	
	/**
	 * 正则取值
	 * @param reg
	 * @param groupInd
	 * @param content
	 * @return
	 */
	public String regexpFetchContent(String reg,int groupInd,String content) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(content);
		if(m.find()) {
			try {
				return m.group(groupInd);
			}catch(Exception e) {
				return null;
			}
		}else {
			return null;
		}
	}
	
	public String processImgUrl(String url,String width,String height) {
		if(url == null) {
			return null;
		}
		
		url = url.replaceFirst("w.h", width+"."+height);
		
		return url;
	}
}
