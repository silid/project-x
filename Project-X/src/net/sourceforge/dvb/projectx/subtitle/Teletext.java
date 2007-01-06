/*
 * @(#)Teletext.java - constants/decode of teletext System B
 *
 * Copyright (c) 2001-2007 by dvb.matt, All Rights Reserved. 
 * 
 * This file is part of ProjectX, a free Java based demux utility.
 * By the authors, ProjectX is intended for educational purposes only, 
 * as a non-commercial test project.
 * 
 *
 * This program is free software; you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package net.sourceforge.dvb.projectx.subtitle;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Arrays;

import net.sourceforge.dvb.projectx.common.Common;
import net.sourceforge.dvb.projectx.common.Keys;

public class Teletext extends Object {

	public Teletext()
	{}

	private final String[] ssaHeader = {
		"[Script Info]",
		"; This is a Sub Station Alpha v4 script.",
		"; For Sub Station Alpha info and downloads,",
		"; go to http://www.eswat.demon.co.uk/",
		"; or email kotus@eswat.demon.co.uk",
		"; to burn-in these subtitles into an AVI, just install subtitler2.3 PlugIn for VirtualDub, see doom9.org",
		"Title: Subtitles taken from TV teletext",
		"Original Script: by their respective owner",
		"ScriptType: v4.00",
		"Collisions: Normal",
		"PlayResY: 240",      // maybe replaced [10]
		"PlayDepth: 0", 
		"Timer: 100.0000",    // maybe replaced [12]
		"[V4 Styles]",
		"Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, TertiaryColour, BackColour, Bold, Italic, BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, AlphaLevel, Encoding",
		"Style: MainB,Arial,14,&H00FFFF,&H00FFFF,&H00FFFF,0,0,-1,1,2,4,1,16,16,16,0,0",
		"Style: MainT,Arial,14,&HFFFFFF,&HFFFFFF,&HFFFFFF,0,1,0,1,2,4,1,16,16,16,0,0",
		"Style: MainI,Arial,14,&HFFFFFF,&HFFFFFF,&HFFFFFF,0,1,1,1,2,4,1,16,16,16,0,0",   //DM30122003 081.6 int10 add
		"Style: MainC,Courier New,14,&HFFFFFF,&HFFFFFF,&HFFFFFF,0,1,0,1,2,4,1,16,16,16,0,0",
		"[Events]",
		"Format: Marked, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text",
		"Comment: Marked=0,0:00:00.00,0:00:00.01,MainB,,0000,0000,0000,!Effect,This script was created by decoding a tv teletext stream to build coloured subtitles"
	};

	private final String[] ssaLine = { 
		"Dialogue: Marked=0,",
		",MainT,,0000,0000,0000,!Effect,{\\q2\\a2}"
	};

	//DM26052004 081.7 int03 changed
	private final String[] stlHeader = {
		"",
		"//Font select and font size",
		"$FontName   = Arial",
		"$FontSize   = 30",
		"//Character attributes (global)",
		"$Bold    = FALSE",
		"$UnderLined = FALSE",
		"$Italic  = FALSE",
		"//Position Control",
		"$HorzAlign = Center",
		"$VertAlign = Bottom",
		"$XOffset   = 10",
		"$YOffset   = 10",
		"//Contrast Control",
		"$TextContrast        = 15",
		"$Outline1Contrast    = 8",
		"$Outline2Contrast    = 15",
		"$BackgroundContrast  = 0",
		"//Effects Control",
		"$ForceDisplay = FALSE",
		"$FadeIn   = 0",
		"$FadeOut  = 0",
		"//Other Controls",
		"$TapeOffset = FALSE",
		"//Colors",
		"$ColorIndex1 = 0",
		"$ColorIndex2 = 1",
		"$ColorIndex3 = 2",
		"$ColorIndex4 = 3",
		"//Subtitles"
	};

	//DM14052004 081.7 int02 add
	private final String[] sonHeader = {
		"st_format\t2",
		"Display_Start\tnon_forced",
		"TV_Type\t\tPAL",
		"Tape_Type\tNON_DROP",
		"Pixel_Area\t(0 575)",
		"Directory\t",
		"",
		"SP_NUMBER\tSTART\t\tEND\t\tFILE_NAME"
	};

	private final String[] colors = {
		"{\\c&HC0C0C0&}",   // black /gray
		"{\\c&H4040FF&}",   // red
		"{\\c&H00FF00&}",   // green
		"{\\c&H00FFFF&}",   // yellow
		"{\\c&HFF409B&}",   // blue //DM15032004 081.6 int18 changed
		"{\\c&HFF00FF&}",   // magenta
		"{\\c&HFFFF00&}",   // cyan
		"{\\c&HFFFFFF&}",   // white
	};


	//DM14052004 081.7 int02 add
	public String[] getSONHead(String path, long frame_rate)
	{
		if (frame_rate != 3600)
		{
			sonHeader[2] = "TV_Type\t\tNTSC";
			sonHeader[3] = "Tape_Type\tDROP";
		}
		else
		{
			sonHeader[2] = "TV_Type\t\tPAL";
			sonHeader[3] = "Tape_Type\tNON_DROP";
		}

		sonHeader[5] = "Directory\t" + path;

		return sonHeader;
	}

	/*****************
	 * return STL header *
	 *****************/
	public String[] getSTLHead(String version)
	{
		stlHeader[0] = 	"//Generated by " + version;

		return stlHeader;
	}

	/*****************
	 * return SSA header *
	 *****************/
	public String[] getSSAHead()
	{ 
		return ssaHeader; 
	}

	/*****************
	 * return SSA line *
	 *****************/
	public String[] getSSALine()
	{ 
		return ssaLine; 
	}

	/*****************
	 * return SMPTE *
 	*****************/
	public String SMPTE(String time, long videoframetime)
	{
		StringBuffer a = new StringBuffer();
		a.append(time.substring(0, 8) + ":00");
		String b = "" + (Integer.parseInt(time.substring(9, 12)) / ((int)videoframetime / 90));
		a.replace((b.length() == 1) ? 10 : 9 , 11, b);

		return a.toString();
	}

	/*****************
	 * change endian *
 	*****************/
	public byte bytereverse(byte n)
	{
		n = (byte) (((n >> 1) & 0x55) | ((n << 1) & 0xaa));
		n = (byte) (((n >> 2) & 0x33) | ((n << 2) & 0xcc));
		n = (byte) (((n >> 4) & 0x0f) | ((n << 4) & 0xf0));
		return n;
	}

	/**************
	 * set parity *
	 **************/
	public byte parity(byte n)
	{
		boolean par=true;

		if (n == 0) 
			return n;

		for (int a=0; a < 8; a++) 
			if ((n>>>a & 1) == 1) 
				par = !par;

		if (par) 
			return (byte)(0x80 | n);

		return n;
	}


	/****************
	 * check parity *
 	****************/
	public boolean cparity(byte n)
	{
		boolean par=true;

		if (n == 0) 
			return true;

		for (int a=0; a < 7; a++) 
			if ((n>>>a & 1) == 1) 
				par = !par;

		if (par && (1 & n>>>7) == 1) 
			return true;

		else if (!par && (1 & n>>>7) == 0) 
			return true;

		return false;
	}


	//DM24052004 081.7 int03 introduced
	//no error correction ATM
	public int hamming_24_18(byte b[], int off)
	{
		int val = 0;

		val |= (0xFE & b[off + 2])>>>1;
		val |= (0xFE & b[off + 1])<<6;
		val |= (0xE & b[off])<<13;
		val |= (0x20 & b[off])<<12;

		return val;
	}

	/******************
	 * hamming decode 8/4 *
	 ******************/
	public int hamming_8_4(byte a)
	{
		switch (0xFF & a)
		{
		case 0x0B: 
			return 1;

		case 0x1C: 
			return 6;

		case 0x26: 
			return 2;

		case 0x31: 
			return 5;

		case 0x40: 
			return 8;

		case 0x57: 
			return 15;

		case 0x6D: 
			return 11;

		case 0x7A: 
			return 12;

		case 0x85: 
			return 3;

		case 0x92: 
			return 4;

		case 0xA8: 
			return 0;

		case 0xBF: 
			return 7;

		case 0xCE: 
			return 10;

		case 0xD9: 
			return 13;

		case 0xE3: 
			return 9;

		case 0xF4: 
			return 14;

		default: 
			return -1;     // decoding error , not yet corrected
		}
	}


	/**
	 * make suppic from teletext *
	 */
	public int[] buildCharArray(byte[] packet, int offset, int len, int row, int character_set, boolean checkParity, boolean boxed_mode, boolean alignment)
	{
		//  return int char<<8 | 0xF0 & active_color backgrnd | 0xF & active_color foregrnd

		boolean ascii = true, toggle = false;
		int chars[] = new int[len];
		int active_color = 7;  // init with white ascii color per line + black background
		int parity_error = 0;
		int[] boxed_area = { -1, -1 };

		int language_code = Common.getSettings().getIntProperty(Keys.KEY_TtxLanguagePair) - 1;

		int primary_set_mapping = language_code < 0 ? 0 : language_code;
		int primary_national_set_mapping = character_set;

		int secondary_set_mapping = primary_set_mapping;
		int secondary_national_set_mapping = primary_national_set_mapping;

		if (page_modifications.containsKey("primary_set"))
			secondary_set_mapping = primary_set_mapping = Integer.parseInt(page_modifications.get("primary_set").toString());

		if (page_modifications.containsKey("primary_national_set"))
			secondary_national_set_mapping = primary_national_set_mapping = Integer.parseInt(page_modifications.get("primary_national_set").toString());

		if (page_modifications.containsKey("secondary_set"))
		{
			secondary_set_mapping = Integer.parseInt(page_modifications.get("secondary_set").toString());
			secondary_national_set_mapping = Integer.parseInt(page_modifications.get("secondary_national_set").toString());
		}

		active_set = CharSet.getActive_G0_Set(primary_set_mapping, primary_national_set_mapping, row);
		active_national_set = CharSet.getActiveNationalSubset(primary_set_mapping, primary_national_set_mapping, row);


		for (int c = offset, val, i = 0; i < len; c++, i++)
		{
			val = row<<16 | i;

			if (page_modifications.containsKey("" + val))
			{
				chars[i] = active_color | (int)(page_modifications.get("" + val).toString().charAt(0))<<8;
				continue;
			}

			//if error, switch to graphics mode (= space)
			if (checkParity && !cparity(packet[c])) 
			{
				parity_error++;
				packet[i] = 8; 
			}

			int char_value = 0x7F & bytereverse(packet[c]);

			if (char_value>>>3 == 0) //0x0..7
			{ 
				ascii=true; 
				chars[i] = (active_set[32]<<8 | active_color); 
				active_color = (0xF0 & active_color) | char_value; 
				continue; 
			}

			else if (char_value>>>4 == 0)  //0x8..F
			{ 
				if (char_value == 0xB) //start box
					boxed_area[0] = i;

				else if (char_value == 0xA) //end box
					if (boxed_area[1] <= boxed_area[0])
						boxed_area[1] = i;

				chars[i] = active_set[32]<<8 | active_color; 
				continue; 
			}

			else if (char_value>>>7 == 1)  //0x80..FF
			{ 
				chars[i] = active_set[32]<<8 | active_color; 
				continue; 
			}

			else if (char_value < 27)  //0x10..1A
			{ 
				ascii = false; 
				chars[i] = active_set[32]<<8 | active_color; 
				continue; 
			}

			else if (char_value < 32) //0x1B..1F
			{
				switch (char_value)    //1d=new bg with last color, 1c=black bg, 1b ESC
				{
				case 0x1B:
					if (toggle)
					{
						active_set = CharSet.getActive_G0_Set(primary_set_mapping, primary_national_set_mapping, row);
						active_national_set = CharSet.getActiveNationalSubset(primary_set_mapping, primary_national_set_mapping, row);
					}
					else
					{
						active_set = CharSet.getActive_G0_Set(secondary_set_mapping, secondary_national_set_mapping, row);
						active_national_set = CharSet.getActiveNationalSubset(secondary_set_mapping, secondary_national_set_mapping, row);
					}
					toggle = !toggle;
					break;

				case 0x1C:
					active_color &= 0xF;
					break;

				case 0x1D:
					active_color |= (0xF & active_color)<<4;
				}

				chars[i] = active_set[32]<<8 | active_color; 
				continue; 
			}

			else if (char_value == 0x7F) //0x7F
			{ 
				chars[i] = active_set[32]<<8 | active_color; 
				continue; 
			}

			if (!ascii)
			{ 
				chars[i] = active_set[32]<<8 | active_color; 
				continue; 
			}

			if (active_national_set != null)
			{
				// all chars 0x20..7F    special characters
				switch (char_value)
				{ 
				case 0x23: 
					chars[i] = active_color | active_national_set[0]<<8;  
					continue; 

				case 0x24: 
					chars[i] = active_color | active_national_set[1]<<8;  
					continue; 

				case 0x40:
					chars[i] = active_color | active_national_set[2]<<8;  
					continue; 

				case 0x5b:
					chars[i] = active_color | active_national_set[3]<<8;  
					continue; 

				case 0x5c: 
					chars[i] = active_color | active_national_set[4]<<8;  
					continue; 

				case 0x5d: 
					chars[i] = active_color | active_national_set[5]<<8;  
					continue; 

				case 0x5e: 
					chars[i] = active_color | active_national_set[6]<<8;  
					continue; 

				case 0x5f: 
					chars[i] = active_color | active_national_set[7]<<8;  
					continue; 

				case 0x60: 
					chars[i] = active_color | active_national_set[8]<<8;  
					continue; 

				case 0x7b: 
					chars[i] = active_color | active_national_set[9]<<8;  
					continue; 

				case 0x7c:
					chars[i] = active_color | active_national_set[10]<<8; 
					continue; 

				case 0x7d:
					chars[i] = active_color | active_national_set[11]<<8; 
					continue; 

				case 0x7e:
					chars[i] = active_color | active_national_set[12]<<8; 
					continue; 
				}
			}

			chars[i] = active_color | active_set[char_value]<<8; 
			continue; 
		}

		if (boxed_mode)
		{
			if (boxed_area[0] >= 0)
			{
				for (int i = 0; i < boxed_area[0] && i < chars.length; i++)
					chars[i] = active_set[32]<<8 | 7; 

				for (int i = boxed_area[1]; i > boxed_area[0] && i < chars.length; i++)
					chars[i] = active_set[32]<<8 | 7; 
			}

			else
				Arrays.fill(chars, (active_set[32]<<8 | 7));
		}

		String test = "";

		for (int s = 0; s < chars.length; s++) 
			test += (char)(chars[s]>>>8);

		// ab 3 paritätsfehlern zeile droppen
		if (checkParity && parity_error > 0)
		{
			String msg = "!> line " + row + ", parity check failed at " + parity_error + " of " + len + " characters: '" + test + "'";

			if (parity_error > Common.getSettings().getIntProperty(Keys.KEY_SubtitlePanel_MaxParityErrors))
			{
				test = "";
				msg += ", line dropped..";
			}

			Common.setMessage(msg);
		}

		int trimlen = test.trim().length();

		if (trimlen == 0) 
			return null;

		else if (trimlen < 40 && alignment)
		{
			int offs = 0;
			int noffs = 0;

			while (test.startsWith(" ", offs))
				offs++;

			noffs = (chars.length - trimlen) / 2;

			System.arraycopy(chars, offs, chars, noffs, trimlen);
			Arrays.fill(chars, 0, noffs, (active_set[32]<<8 | 7));
			Arrays.fill(chars, noffs + trimlen, chars.length, (active_set[32]<<8 | 7));
		}

		return chars;
	}

	/**
	 * make strings from teletext
	 */
	public String buildString(byte[] packet, int offset, int len, int row, int character_set, int color, boolean checkParity)
	{
		return buildString(packet, offset, len, row, character_set, color, checkParity, false);
	}

	/**
	 * make strings from teletext
	 */
	public String buildString(byte[] packet, int offset, int len, int row, int character_set, int color, boolean checkParity, boolean boxed_mode)
	{
		boolean ascii = true;
		boolean toggle = false;

		StringBuffer line_buffer = new StringBuffer();
		ColorIndex color_index;
		ArrayList color_list = new ArrayList();

		int parity_error = 0;
		int[] boxed_area = { -1, -1 };

		int language_code = Common.getSettings().getIntProperty(Keys.KEY_TtxLanguagePair) - 1;

		int primary_set_mapping = language_code < 0 ? 0 : language_code;
		int primary_national_set_mapping = character_set;

		int secondary_set_mapping = primary_set_mapping;
		int secondary_national_set_mapping = primary_national_set_mapping;

		if (page_modifications.containsKey("primary_set"))
			secondary_set_mapping = primary_set_mapping = Integer.parseInt(page_modifications.get("primary_set").toString());

		if (page_modifications.containsKey("primary_national_set"))
			secondary_national_set_mapping = primary_national_set_mapping = Integer.parseInt(page_modifications.get("primary_national_set").toString());

		if (page_modifications.containsKey("secondary_set"))
		{
			secondary_set_mapping = Integer.parseInt(page_modifications.get("secondary_set").toString());
			secondary_national_set_mapping = Integer.parseInt(page_modifications.get("secondary_national_set").toString());
		}

		active_set = CharSet.getActive_G0_Set(primary_set_mapping, primary_national_set_mapping, row);
		active_national_set = CharSet.getActiveNationalSubset(primary_set_mapping, primary_national_set_mapping, row);

		loopi:
		for (int c = offset, val, i = 0; i < len; c++, i++)
		{
			val = row<<16 | i;

			if (page_modifications.containsKey(String.valueOf(val)))
			{
				line_buffer.append(page_modifications.get(String.valueOf(val)));
				continue;
			}

			//if error, switch to graphics mode (= space), by loosing all following chars
			if (checkParity && !cparity(packet[c]))
			{
				parity_error++;
				packet[c] = 8; 
			}

			int char_value = 0x7F & bytereverse(packet[c]);

			if (char_value>>>3 == 0)  //0x0..7
			{ 
				ascii = true; 
				//line_buffer.append(color == 1 ? colors[char_value] : "");

				if (color == 1)
					color_list.add(new ColorIndex(i, char_value));

				line_buffer.append((char)active_set[32]);
				continue; 
			}

			else if (char_value>>>4 == 0)   //0x8..F
			{ 
				if (char_value == 0xB) //start box
					boxed_area[0] = i;

				else if (char_value == 0xA) //end box
					if (boxed_area[1] <= boxed_area[0])
						boxed_area[1] = i;

				line_buffer.append((char)active_set[32]);
				continue; 
			}

			else if (char_value>>>7 == 1)  //0x80..FF
			{ 
				line_buffer.append((char)active_set[32]);
				continue; 
			}

			else if (char_value < 27)  //0x10..1A
			{ 
				ascii = false; 
				line_buffer.append((char)active_set[32]);
				continue; 
			}

			else if (char_value < 32) //0x1B..1F
			{  
				if (char_value == 0x1B) //ESC
				{
					if (toggle)
					{
						active_set = CharSet.getActive_G0_Set(primary_set_mapping, primary_national_set_mapping, row);
						active_national_set = CharSet.getActiveNationalSubset(primary_set_mapping, primary_national_set_mapping, row);
					}

					else
					{
						active_set = CharSet.getActive_G0_Set(secondary_set_mapping, secondary_national_set_mapping, row);
						active_national_set = CharSet.getActiveNationalSubset(secondary_set_mapping, secondary_national_set_mapping, row);
					}

					toggle = !toggle;
				}

				line_buffer.append((char)active_set[32]);
				continue; 
			}

			else if (char_value == 0x7F) //0x7F
			{  
				line_buffer.append((char)active_set[32]);
				continue; 
			}

			if (!ascii)
			{ 
				line_buffer.append((char)active_set[32]);
				continue; 
			}


			if (active_national_set != null)
			{
				// all chars 0x20..7F
				switch (char_value)  // special national characters
				{
				case 0x23:
					line_buffer.append((char)active_national_set[0]);
					continue loopi; 

				case 0x24:
					line_buffer.append((char)active_national_set[1]);
					continue loopi; 

				case 0x40:
					line_buffer.append((char)active_national_set[2]);
					continue loopi; 

				case 0x5b:
					line_buffer.append((char)active_national_set[3]);
					continue loopi; 

				case 0x5c:
					line_buffer.append((char)active_national_set[4]);
					continue loopi; 

				case 0x5d:
					line_buffer.append((char)active_national_set[5]);
					continue loopi; 

				case 0x5e:
					line_buffer.append((char)active_national_set[6]);
					continue loopi; 

				case 0x5f:
					line_buffer.append((char)active_national_set[7]);
					continue loopi; 

				case 0x60:
					line_buffer.append((char)active_national_set[8]);
					continue loopi; 

				case 0x7b:
					line_buffer.append((char)active_national_set[9]);
					continue loopi; 

				case 0x7c:
					line_buffer.append((char)active_national_set[10]);
					continue loopi; 

				case 0x7d:
					line_buffer.append((char)active_national_set[11]);
					continue loopi; 

				case 0x7e:
					line_buffer.append((char)active_national_set[12]);
					continue loopi; 
				}
			}

			line_buffer.append((char)active_set[char_value]);

			continue loopi; 
		}

		// ab 3 paritätsfehlern zeile droppen
		if (checkParity && parity_error > 0)
		{
			String msg = "!> line " + row + ", parity check failed at " + parity_error + " of " + len + " characters: '" + line_buffer.toString() + "'";

			if (parity_error > Common.getSettings().getIntProperty(Keys.KEY_SubtitlePanel_MaxParityErrors))
			{
				line_buffer.setLength(0);
				color = 0;
				msg += ", line dropped..";
			}

			Common.setMessage(msg);
		}

		if (boxed_mode)
		{
			if (boxed_area[0] >= 0 && line_buffer.length() > 0)
			{
				for (int i = 0; i < boxed_area[0]; i++)
					line_buffer.setCharAt(i, (char)active_set[32]);

				for (int i = boxed_area[1]; i > boxed_area[0] && i < line_buffer.length(); i++)
					line_buffer.setCharAt(i, (char)active_set[32]);
			}

			else
				line_buffer.setLength(0);
		}

		//insert color strings heading
		if (color == 1 && line_buffer.length() > 0)
		{
			ColorIndex ci;

			for (int i = color_list.size() - 1; i >= 0; i--)
			{
				ci = (ColorIndex) color_list.get(i);
				line_buffer.insert(ci.getIndex(), colors[ci.getColor()]);
			}
		}

		String line = line_buffer.toString();

		if (color == 1)
		{
			line = line.trim();

			if (line.length() > 0)
				line = colors[7] + line;
		}

		return line;
	}

	//DM30072004 081.7 int07 add
	private Hashtable page_modifications = new Hashtable();
	private boolean use = false;
	private int display_row = 0, display_column = 0;
	private short active_set[];
	private short active_national_set[];


	//DM30072004 081.7 int07 add
	public void clearEnhancements()
	{
		page_modifications.clear();
		use = false;
		display_row = 0;
		display_column = 0;
		active_set = CharSet.getActive_G0_Set(0, 0, 0);
		active_national_set = CharSet.getActiveNationalSubset(0, 0, 0);
	}

	//analyze triplets etc.
	//DM30072004 081.7 int07 add
	public void setEnhancements(byte packet[], int row, int character_set)
	{
		int val, mapping, position = 0, code;
		byte address, mode, data, designation;

		designation = bytereverse((byte)((0xF & hamming_8_4(packet[6]))<<4));

		//Common.setMessage("row " + row + " /designation " + designation);

		if ((row == 29 && designation == 0) || (row == 29 && designation == 4) || (row == 28 && designation == 4))
		{
			// read triplet 1
			val = hamming_24_18(packet, 7);
			code = val<<3;

			if (row == 28 && designation == 0 && (0x3F800 & val) != 0)
				return;  // not X/28/0 format 1

			// read triplet 2
			val = hamming_24_18(packet, 10);
			code |= (7 & val>>15);

			//primary set
			mapping = 0xFE & bytereverse((byte)(0x7F & code>>7));

			page_modifications.put("primary_set", "" + (0xF & mapping>>4));

			if (row != 29)
				page_modifications.put("primary_national_set", "" + character_set);

			//secondary set
			mapping = 0xFE & bytereverse((byte)(0x7F & code));

			page_modifications.put("secondary_set", "" + (0xF & mapping>>4));
			page_modifications.put("secondary_national_set", "" + (7 & mapping>>1));
		}

		if (row != 26)
			return;

		for (int a = 7; a < 46; a += 3)
		{
			val = hamming_24_18(packet, a);
			address = bytereverse( (byte)(0xFC & val>>10));
			mode = bytereverse( (byte)(0xF8 & val>>4));
			data = bytereverse( (byte)(0xFE & val<<1));

		/**
			Common.setMessage("triplet " + a + " / " + Integer.toBinaryString(val));
			Common.setMessage("  address " + address );
			Common.setMessage("  mode " + mode );
			Common.setMessage("  data " + data );
		**/

			if (address >= 40)  //40..63 means row 24,1..23
			{
				if (address == 63 && mode == 31) //termination
					break;

				if (mode != 4 && mode != 1)
				{
					use = false;
					continue;
				}

				display_row = address == 40 ? 0 : address - 40;
				display_column = mode == 1 ? 0 : data;
				use = true;
			}
			else //0..39 means column 0..39
			{
				if (!use)
					continue;

				display_column = address;
				String str = "";

				if (mode == 15) //char from G2 set
					str += (char)CharSet.getActive_G2_Set(0, character_set, display_row)[data];

				else if (mode == 16) //char from G0 set w/o diacr.
					str += (char)CharSet.getActive_G0_Set(0, character_set, display_row)[data];

				//combination fixed table (because it won't work here when combine unicode chars)
				else if (mode > 16) //char from G0 set w/ diacr.
					str += (char)CharSet.getCombinedCharacter(data, mode & 0xF);

				else
					continue;

				position = display_row<<16 | display_column;

				page_modifications.put("" + position, str);

				//Common.setMessage("replaced char " + str + " /m " + mode);
			}
		}
	}

	/**
	 * 
	 */
	private class ColorIndex {

		private int index;
		private int color;

		public ColorIndex(int val_1, int val_2)
		{
			index = val_1;
			color = val_2;
		}

		public int getIndex()
		{
			return index;
		}

		public int getColor()
		{
			return color;
		}
	}
}
