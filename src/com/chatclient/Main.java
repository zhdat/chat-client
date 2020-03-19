package com.chatclient;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.*;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
		Screen screen = null;
		try {
			Terminal terminal = defaultTerminalFactory.createTerminal();
			screen = new TerminalScreen(terminal);
			screen.startScreen();
			screen.setCursorPosition(null);
			
			int row=0;
			
			TerminalPosition textTopRightCorner = new TerminalPosition(1,0);
			TerminalSize textBox = new TerminalSize(terminal.getTerminalSize().getColumns() - 2, terminal.getTerminalSize().getRows() - 2);
			
			
			
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if(screen != null) {
				try {
					screen.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		
	}
}
