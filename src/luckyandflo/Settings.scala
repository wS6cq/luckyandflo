package luckyandflo

import java.io.File

/**
 * Copyright (c) 2011, Phillip Kroll <contact@phillipkroll.de>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 * 
 */

object Settings {

  /**
   * Read settings from command line arguments
   */
  def readArgs(args: Array[String]):Settings = {

    // --rules D:\mydocs\phillip\hbrs\007-dev\luckyandflo\rules.xml
    // --directory D:\mydocs\phillip\docroot\typo3-430-alpha3
    // --output C:\output

    val settings = new Settings

    if (args.length >= 6) {
      for (i <- 0 to args.length-1 by 2){
        if (args(i) == "--rules" ) settings.rules = args(i+1)
        if (args(i) == "--directory" ) settings.directory = args(i+1)
        if (args(i) == "--output" ) settings.output = args(i+1)
        if (args(i) == "--extensions" ) settings.extensions = args(i+1).split(",")
        if (args(i) == "--maxdepth" ) settings.maxDepth = args(i+1).toInt
        if (args(i) == "--maxfiles" ) settings.maxFiles = args(i+1).toInt
      }
    }
    settings
  }

}

/**
 * Contains settings to configure the behaviour of the program
 */
class Settings {

   var directory = ""
   var output = ""
   var rules = ""
   var maxDepth = 8
   var maxFiles = 9999
   var extensions = Array[String]("php", "html", "css", "js", "java", "scala")

   override def toString() = {
    "Your input was:\n" +
    "\tRules: " + rules + "\n" +
    "\tDirectory: " + directory + "\n" +
    "\tOutput: " + output + "\n"

   }

  def complete():Boolean = {
    var complete =
      !(rules equals "") &&
      !(directory equals "") &&
      !(output equals "")

    if (complete) {
      complete &= (new File(rules)).isFile()
      complete &= (new File(directory)).isDirectory()
      complete &= (new File(output)).isDirectory()
    }
    complete
  }

}