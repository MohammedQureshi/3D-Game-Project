package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float distanceFromPlayer = 70;
	private float angleAroundPlayer = 0;
	///////////////////////////////////////////////////////////////////////////////////////////////
	//                                       X   Y   Z
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch  = 15;
	private float yaw = 0;
	private float roll;
	
	private Player player;

	public Camera(Player player) {
		this.player = player;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	//Movement of player and speed.
	
	public void move(){
		if(Player.movePlayerWithMouse) {		
			calculateZoom();
			calculatePitch();
			calculateAngleAroundPlayer();
			float horizontalDistance = calculateHorizontalDistance();
			float verticalDistance = calculateVerticalDistance();
			calculateCameraPosition(horizontalDistance,verticalDistance);
			this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
		}
	}
	public void invertPitch() {
		this.pitch = -pitch;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horizDistance, float verticDistance){
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance + 10;
	}
	
	private float calculateHorizontalDistance(){
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance(){
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	public void calculateZoom(){
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
		if(distanceFromPlayer < 10) {
			distanceFromPlayer = 10;
		}else if(distanceFromPlayer > 160) {
			distanceFromPlayer = 160;
		}
	}
	
	public void calculatePitch(){
		float pitchChange = Mouse.getDY() * 0.2f;
		pitch -= pitchChange;
		if(pitch < 0){
			pitch = 0;
		}else if(pitch > 90){
			pitch = 90;
		}
	}
	
	private void calculateAngleAroundPlayer(){
		if(Mouse.isButtonDown(0)){
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
}

