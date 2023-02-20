package me.rpgarnet.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;



public class HexColor {
	
	public static final Pattern HEX_PATTERN = Pattern.compile("&(#[A-Fa-f0-9]{6})");
    public static final char COLOR_CHAR = ChatColor.COLOR_CHAR;
    public static final int CENTER_PX = 60;

    public static String translateHexColorCodes(String startTag, String endTag, String message) {
        final Pattern hexPattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})" + endTag);
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
                    );
        }
        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }
    
    public static String translateHexColorCodes(String message) {
        return translateHexColorCodes("&#", "", message);
    }
    
    public static String centeredMessage(String message) {
            if(message == null || message.equals("")) return message;
                    message = translateHexColorCodes(message);
                   
                    int messagePxSize = 0;
                    boolean previousCode = false;
                    boolean isBold = false;
                   
                    for(char c : message.toCharArray()){
                            if(c == '§'){
                                    previousCode = true;
                                    continue;
                            }else if(previousCode == true){
                                    previousCode = false;
                                    if(c == 'l' || c == 'L'){
                                            isBold = true;
                                            continue;
                                    }else isBold = false;
                            }else{
                                    DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                                    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                                    messagePxSize++;
                            }
                    }
                   
                    int halvedMessageSize = messagePxSize / 2;
                    int toCompensate = CENTER_PX - halvedMessageSize;
                    int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
                    int compensated = 0;
                    String sb = "                    ";
                    while(compensated < toCompensate){
                            sb = sb + " ";
                            compensated += spaceLength;
                    }
                    
                    return sb + message;
            }
    
}
