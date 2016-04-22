package view;

import javafx.scene.paint.Color;

public class Settings
{
	public static final double ICON_WIDTH_SIZE = 25;
	public static final double NODE_SIZE = 50;
	public static final int PADDING_IN_BAR = 5;
	public static final int CONTENT_AREA_WIDTH = 800;
	public static final int CONTENT_AREA_HEIGHT = 400;
	public static final Color DIFFUSE_COLOR = Color.rgb(51, 102, 153);// CSS: #336699
	public static final Color SPECULAR_COLOR = Color.rgb(0, 153, 204);// CSS: #0099cc
	public static final String CSS_PATH = "resource/stylesheets.css"; // Note that this file is read from the Main.java
	public static final String IMAGE_APP_ICON = "resource/ic_share_48pt_3x.png"; // Note that this file is read from the Main.java
	public static final String IMAGE_BNEW = "../resource/ic_add_box_48pt_3x.png";
	public static final String IMAGE_BLOAD = "../resource/ic_unarchive_48pt_3x.png";
	public static final String IMAGE_BSAVE = "../resource/ic_archive_48pt_3x.png";
	public static final String IMAGE_BPAUSE = "../resource/ic_pause_circle_outline_48pt_3x.png";
	public static final String IMAGE_BPLAY = "../resource/ic_play_circle_outline_48pt_3x.png";
	public static final String IMAGE_BSTEP = "../resource/ic_skip_next_48pt_3x.png";
	public static final String IMAGE_BSTOP = "../resource/ic_stop_48pt_3x.png";
	public static final String IMAGE_MITEM_CONNECT = "../resource/ic_linear_scale_48pt_3x.png";
	public static final String IMAGE_MITEM_DISCONNECT = "../resource/ic_more_horiz_48pt_3x.png";
	public static final String IMAGE_MITEM_EDIT = "../resource/ic_create_48pt_3x.png";
	public static final String IMAGE_MITEM_DELETE = "../resource/ic_delete_forever_48pt_3x.png";
	public static final String IMAGE_MITEM_STARTPOINT = "../resource/ic_flight_takeoff_48pt_3x.png";
	public static final String IMAGE_MITEM_ENDPOINT = "../resource/ic_flight_land_48pt_3x.png";
}
