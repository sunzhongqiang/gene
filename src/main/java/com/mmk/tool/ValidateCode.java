package com.mmk.tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码图片生成类
 * 
 * @Title: 验证码图片生成类
 * @Description: 提供生成验证码图片的方法
 * @Copyright: Copyright (c) 2011
 * @Company: lvbang
 * @author lvgang
 * @version 1.0
 */

public class ValidateCode {
	static Random r = new Random();

	static String ssource = "0123456789";

	static char[] src = ssource.toCharArray();

	/**
	 * 私有的产生随即字符串的方法
	 * 
	 * @param 字符串长度
	 * @return 随即的字符串
	 */
	private static String randString(int length) {
		char[] buf = new char[length];
		int rnd;
		for (int i = 0; i < length; i++) {
			rnd = Math.abs(r.nextInt()) % src.length;

			buf[i] = src[rnd];
		}
		return new String(buf);
	}

	/**
	 * 产生随机字符串的方法
	 * 
	 * @param i
	 *            字符串的长度
	 * @return 生成的字符串
	 */
	public String runValidateCode(int i) {
		String ValidateCode = randString(i);
		return ValidateCode;
	}

	/**
	 * 给定范围获得随机颜色
	 * 
	 * @param fc
	 *            色彩范围
	 * @param bc
	 *            色彩范围
	 * @return 生成的颜色
	 */
	public Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 验证码生成图象
	 * 
	 * @param sCode
	 *            传递验证码
	 * @return 生成的图像
	 */
	public BufferedImage CreateImage(String sCode) {
		try {

			// 字符的字体
			Font CodeFont = new Font("Times New Roman", Font.BOLD, 16);

			int iLength = sCode.length(); // 得到验证码长度
			int width = 14 * iLength, height = 20; // 图象宽度与高度
			int CharWidth = (int) (width - 14) / iLength; // 字符距左边宽度
			int CharHeight = 16; // 字符距上边高度
			// 在内存中创建图象
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			// 获取图形上下文
			Graphics g = image.getGraphics();

			// 生成随机类
			Random random = new Random();
			// 设定背景色
			g.setColor(getRandColor(225, 250));
			g.fillRect(0, 0, width, height);
			// 设定字体
			g.setFont(CodeFont);
			// 画随机颜色的边框
			g.setColor(getRandColor(10, 50));
			// g.drawRect(0,0,width-1,height-1);
			// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
			g.setColor(new Color(50, 205, 50));

			for (int i = 0; i < 20; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(200);
				int yl = random.nextInt(200);
				if (i % 2 == 0) {
					g.drawLine(x, y, x + xl, y + yl);
				} else {
					g.drawLine(x, y, x - xl, y + yl);
				}
			}
			for (int i = 0; i < iLength; i++) {
				String rand = sCode.substring(i, i + 1);
				// 将认证码显示到图象中
				g.setColor(Color.BLACK);
				g.drawString(rand, CharWidth * i + 7, CharHeight);
			}
			// 图象生效
			g.dispose();
			return image;
		} catch (Exception e) {

		}
		return null;
	}
}
