/*
 * @(#)Common.java - carries various stuff 
 *
 * Copyright (c) 2004 by dvb.matt. 
 * 
 * This file is part of X, a free Java based demux utility.
 * X is intended for educational purposes only, as a non-commercial test project.
 * It may not be used otherwise. Most parts are only experimental.
 *  
 *
 * This program is free software; you can redistribute it free of charge
 * and/or modify it under the terms of the GNU General Public License as published by
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

//package X

import java.io.*;
import java.awt.*;
import javax.swing.*;

//DM13042004 081.7 int01 introduced
public final class Common
{
	private Common()
	{}

	// should try a while to rename with success, if file_system is blocked by another app.
	public static void renameTo(File oldfile, File newfile)
	{
		for (int a = 0; a < 10000; a++)
			if ( oldfile.renameTo(newfile) )
				return;

		X.Msg("!> cannot rename " + oldfile.toString() + " to " + newfile.toString());
		X.TextArea.setBackground(new Color(255,225,225));
	}

	public static void renameTo(String oldfile, String newfile)
	{
		renameTo(new File(oldfile), new File(newfile));
	}
}