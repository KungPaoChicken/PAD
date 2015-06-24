package assignment6.View;

import assignment6.ClusterRow;

public interface View {
	
	/**
	 * Draws a visual representation of the clusters.
	 * @param clusters The clusters of which a visualisation is to be drawn.
	 */
	public void draw(ClusterRow clusters);
}
