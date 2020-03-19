package com.chatclient.console;

import java.io.IOException;
import java.util.Arrays;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyType;

public class ChatInput {
	Screen screen;
	TextGraphics textGraphics;
	boolean replaceChar;
	
	public ChatInput(Screen screen, TextGraphics textGraphics, boolean replaceChar) throws IOException {
		this.screen = screen;
		this.textGraphics = textGraphics;
		this.replaceChar = replaceChar;
		
		TerminalPosition boxLeft = new TerminalPosition(0, this.screen.getTerminalSize().getRows() - 1);
		TerminalPosition boxRight = boxLeft.withColumn(this.screen.getTerminalSize().getColumns());
		
		TextCharacter inputChar = new TextCharacter('>');
		this.textGraphics.setCharacter(boxLeft, inputChar);
		this.textGraphics.drawLine(boxLeft, boxRight, Symbols.SINGLE_LINE_HORIZONTAL);
		this.screen.refresh();
	}
	
	public char[] getInput() {
		while(true) {
			char[] lineBuffer;
			char[] buf;
			
			int drawOffset = 1;
			
			
			buf = lineBuffer = new char[128];
			
			int room = buf.length;
			int offset = 0;
			KeyStroke c;
			
			try {
				while(true) {
					TerminalPosition boxLeft = new TerminalPosition(0, this.screen.getTerminalSize().getRows() - 1);
					TerminalPosition boxRight = boxLeft.withColumn(this.screen.getTerminalSize().getColumns());
					TerminalPosition cursorPos = new TerminalPosition(drawOffset, this.screen.getTerminalSize().getRows());
					
					TextCharacter inputChar = new TextCharacter('>');
					this.textGraphics.setCharacter(boxLeft, inputChar);
					this.textGraphics.drawLine(boxLeft, boxRight, Symbols.SINGLE_LINE_HORIZONTAL);
					
					c = this.screen.pollInput();
					if (c.getKeyType() == KeyType.Enter) {
						this.textGraphics.drawLine(boxLeft, boxRight, ' ');
						break;
					}
					if (c.getKeyType() == KeyType.Character) {
						if (--room < 0) {
							buf = new char[offset + 128];
							room = buf.length - offset - 1;
							System.arraycopy(lineBuffer, 0, buf, 0, offset);
							Arrays.fill(lineBuffer, ' ');
							lineBuffer = buf;
						}
						buf[offset++] = c.getCharacter();
						if (this.replaceChar) {
							TextCharacter replaceCharacter = new TextCharacter('*');
							this.screen.setCharacter(cursorPos, replaceCharacter);
							drawOffset++;
						} else {
							TextCharacter nextChar = new TextCharacter(c.getCharacter());
							this.screen.setCharacter(cursorPos, nextChar);
							drawOffset++;
						}
					}
					this.screen.refresh();
				}
				if (offset == 0) {
					return null;
				}
				char[] ret = new char [offset];
				System.arraycopy(buf, 0, ret, 0, offset);
				Arrays.fill(buf, ' ');
				return ret;
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}