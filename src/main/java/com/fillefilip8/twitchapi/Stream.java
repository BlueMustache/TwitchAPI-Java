package com.fillefilip8.twitchapi;

public class Stream {
	private int viewers;
	private String status = "";
	private String game = "";
	private String channelName;
private static Channel channel;
	public Stream(String channelName, String game, int viewers, String status) {
		this.game = game;
		this.status = status;
		this.viewers = viewers;
		this.channelName = channelName;
	}

	/**
	 * Get the channel of the stream
	 * 
	 * @return
	 */
	public Channel getChannel() {
		return new Channel(channelName);
		

	}

	public int getViewers() {
		return viewers;
	}

	/**
	 * Get the title/status of the stream
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;

	}

	public String getGame() {
		return game;

	}

}
