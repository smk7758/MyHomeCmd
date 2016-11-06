/*
 * @author     kazu0617
 * @license    MIT
 * @copyright  Copyright kazu0617 2015
 */
package com.github.smk7758.MyHomeCmd;

import java.util.logging.Logger;
import org.bukkit.command.CommandSender;

/**
 * �P�Ǝ��s(Util�s�g�p)�̂�
 */
public class ConsoleLog {
	private Main plugin;
	public static final Logger log = Logger.getLogger("Minecraft");

	public ConsoleLog(Main instance) {
		plugin = instance;
	}

	/**
	 * �R���\�[��(Log)�Ƀ��b�Z�[�W�𑗂�B
	 * (����Ɏx�Ⴊ���������o�������ŗǂ���)
	 *
	 * @param msg ���b�Z�[�W
	 */
	public void info(String msg) {
		log.info(plugin.cPrefix + msg);
	}

	/**
	 * �R���\�[��(Log)�Ƀf�o�b�O���b�Z�[�W�𑗂�B
	 *
	 * @param msg ���b�Z�[�W
	 */
	public void debug(String msg) {
		if (plugin.DebugMode) log.info(plugin.cPrefix + "[Debug] " + msg);
	}

	/**
	 * �R���\�[��(Log)�Ɍx�����b�Z�[�W�𑗂�B
	 * (����Ɏx�Ⴊ���鎞)
	 *
	 * @param msg ���b�Z�[�W
	 */
	public void warn(String msg) {
		log.warning(plugin.cPrefix + msg);
	}

	/**
	 * ���b�Z�[�W�𑗂�B
	 *
	 * @param sender ����
	 * @param msg ���b�Z�[�W
	 * @param mode 0 PluginPrefix([PluginName]) ���ʂ̃��b�Z�[�W
	 * @param mode 1 cPrefix pInfo �R���\�[���ւ̃��b�Z�[�W
	 * @param mode 2 cPrefix pError �G���[���b�Z�[�W
	 * @param mode 3 cPrefix [Debug] �f�o�b�O���b�Z�[�W
	 */
	public void sendMessage(CommandSender sender, String msg, int mode) {
		if (mode == 0) sender.sendMessage(plugin.PluginPrefix + msg);
		if (mode == 1) sender.sendMessage(plugin.cPrefix + plugin.pInfo + msg);
		if (mode == 2) sender.sendMessage(plugin.cPrefix + plugin.pError + msg);
		if (mode == 3 && plugin.DebugMode) sender.sendMessage(plugin.cPrefix + "[Debug]" + msg);
	}

	public void sendPermissionErrorMessage(CommandSender sender, String permission) {
		sender.sendMessage(plugin.cPrefix + plugin.pError + "You don't have Permission.");
		if (plugin.DebugMode) sender.sendMessage(plugin.cPrefix + "[Debug] Permission:" + permission);
	}

	public void sendBroadCast(String msg) {
		plugin.getServer().broadcastMessage(plugin.PluginPrefix + msg);
	}
}
