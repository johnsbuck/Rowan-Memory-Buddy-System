package binarybuddysystem.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;

public class ProcessLabel extends JLabel
{
	Color myColor;
	public ProcessLabel(String text, Color col)
	{
		super(text);
		myColor = col;
		setIcon(new ProcessIcon());
		setMinimumSize(new Dimension(400,13));
		setPreferredSize(new Dimension(400,13));
	}
	
	private class ProcessIcon implements Icon
	{
		@Override
		public int getIconHeight()
		{
			return getHeight();
		}

		@Override
		public int getIconWidth()
		{
			return getHeight();
		}

		@Override
		public void paintIcon(Component comp, Graphics g, int x, int y)
		{
			g.setColor(myColor);
			g.fillRect(x, y, getIconWidth(), getIconWidth());
		}
	}
}
