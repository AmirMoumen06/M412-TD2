/*
 * M412 2020-2021: distributed programming
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordRun {
	private String passEncrypted;
	private String passUncrypted = null;
	private int uncryptedLength;

	private PasswordRun(String passUncrypted)
			throws NoSuchAlgorithmException {
		this.passEncrypted = encryptPassword(passUncrypted);
		this.uncryptedLength = passUncrypted.length();
		this.passUncrypted = passUncrypted;
	}

	private PasswordRun(String passEncrypted, int uncryptedLength) {
		this.passEncrypted = passEncrypted;
		this.uncryptedLength = uncryptedLength;
	}

	/**
	 * compute the 16 bytes md5 digest of a string see
	 * http://docs.oracle.com/javase
	 * /7/docs/technotes/guides/security/crypto/CryptoSpec.html
	 * 
	 * @param pass : string digest
	 * @return hex representation of the digest
	 * @throws NoSuchAlgorithmException : not in the API
	 */
	private String encryptPassword(String pass) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");

		byte[] passBytes = pass.getBytes();

		md.update(passBytes);
		byte[] digest = md.digest(passBytes);

		StringBuilder sb = new StringBuilder();
		for (byte b : digest) { // convert to hex
			sb.append("0123456789ABCDEF".charAt((b & 0xF0) >> 4));
			sb.append("0123456789ABCDEF".charAt((b & 0x0F)));
		}
		return sb.toString();
	}

	private String getPassEncrypted() {
		return passEncrypted;
	}

	public String getPassUncrypted() {
		return passUncrypted;
	}

	private int getUncryptedLength() {
		return uncryptedLength;
	}

	private String generateRandomWord(int wordLength) {
		Random r = new Random(); // Initialize a Random Number Generator with
									// SysTime as the seed
		StringBuilder sb = new StringBuilder(wordLength);
		for (int i = 0; i < wordLength; i++) { // For each letter in the word
			char tmp = (char) ('a' + r.nextInt(26)); // Generate a letter
														// between a and z
			sb.append(tmp); // Add it to the String
		}
		return sb.toString();
	}

	private String randomSearch(int passLength) throws NoSuchAlgorithmException {
		System.out.println(Thread.currentThread().getName() + " cherche "
				+ this.passUncrypted);
		while (true) {
			String guess = generateRandomWord(passLength);
			PasswordRun crack = new PasswordRun(guess);
			if (this.passEncrypted.equals(crack.passEncrypted)) {
				System.out.println("your password is: " + guess);
				return guess;
			}
		}
	}
	
	private void run(String[] passList) {
		try {
            this.getPassEncrypted();
             this.randomSearch(this.getUncryptedLength());
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	

	public static void main(String[] args) throws
			NoSuchAlgorithmException {

		String[] passList = { "aaa", "bbbb", "ccc", "ddd", "eee", "fff", "ggg",
				"hhh", "iii", "jjjj", "kkk", "lll", "azert"};

		for (String s : passList) {
			PasswordRun mcpU = new PasswordRun(s);
			System.out.println(s + "(" + mcpU.getPassEncrypted()
					+ ") <=> " + mcpU.randomSearch(mcpU.getUncryptedLength()));
		}

		PasswordRun mcpE = new PasswordRun(
				"4BB2C9D9A57A0D2A53E7C4D56C952331", 4);
		System.out.println("4BB2C9D9A57A0D2A53E7C4D56C952331" + ") <=> "
				+ mcpE.randomSearch(mcpE.getUncryptedLength()));
		
		mcpE.run(passList);

	}
}