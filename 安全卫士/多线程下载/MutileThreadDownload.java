import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MutileThreadDownload {
	/**
	 * �̵߳�����
	 */
	private static int threadCount = 3;

	/**
	 * ÿ����������Ĵ�С
	 */
	private static long blocksize;

	/**
	 * �������е��̵߳�����
	 */
	private static int runningThreadCount;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// �������ļ���·��
		String path = "http://192.168.1.100:8080/ff.exe";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		int code = conn.getResponseCode();
		if (code == 200) {
			long size = conn.getContentLength();// �õ�����˷��ص��ļ��Ĵ�С
			System.out.println("�������ļ��Ĵ�С��" + size);
			blocksize = size / threadCount;
			// 1.�����ڱ��ش���һ����С��������һģһ���Ŀհ��ļ���
			File file = new File("temp.exe");
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.setLength(size);
			// 2.�������ɸ����̷ֱ߳�ȥ���ض�Ӧ����Դ��
			runningThreadCount = threadCount;
			for (int i = 1; i <= threadCount; i++) {
				long startIndex = (i - 1) * blocksize;
				long endIndex = i * blocksize - 1;
				if (i == threadCount) {
					// ���һ���߳�
					endIndex = size - 1;
				}
				System.out.println("�����̣߳�" + i + "���ص�λ�ã�" + startIndex + "~"
						+ endIndex);
				new DownloadThread(path, i, startIndex, endIndex).start();
			}
		}
		conn.disconnect();
	}

	private static class DownloadThread extends Thread {
		private int threadId;
		private long startIndex;
		private long endIndex;
		private String path;

		public DownloadThread(String path, int threadId, long startIndex,
				long endIndex) {
			this.path = path;
			this.threadId = threadId;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}

		@Override
		public void run() {
			try {
				// ��ǰ�߳����ص��ܴ�С
				int total = 0;
				File positionFile = new File(threadId + ".txt");
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				// ���Ŵ���һ�ε�λ�ü�����������
				if (positionFile.exists() && positionFile.length() > 0) {// �ж��Ƿ��м�¼
					FileInputStream fis = new FileInputStream(positionFile);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(fis));
					// ��ȡ��ǰ�߳��ϴ����ص��ܴ�С�Ƕ���
					String lasttotalstr = br.readLine();
					int lastTotal = Integer.valueOf(lasttotalstr);
					System.out.println("�ϴ��߳�" + threadId + "���ص��ܴ�С��"
							+ lastTotal);
					startIndex += lastTotal;
					total += lastTotal;// �����ϴ����ص��ܴ�С��
					fis.close();
				}

				conn.setRequestProperty("Range", "bytes=" + startIndex + "-"
						+ endIndex);
				conn.setConnectTimeout(5000);
				int code = conn.getResponseCode();
				System.out.println("code=" + code);
				InputStream is = conn.getInputStream();
				File file = new File("temp.exe");
				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				// ָ���ļ���ʼд��λ�á�
				raf.seek(startIndex);
				System.out.println("��" + threadId + "���̣߳�д�ļ��Ŀ�ʼλ�ã�"
						+ String.valueOf(startIndex));
				int len = 0;
				byte[] buffer = new byte[512];
				while ((len = is.read(buffer)) != -1) {
					RandomAccessFile rf = new RandomAccessFile(positionFile,
							"rwd");
					raf.write(buffer, 0, len);
					total += len;
					rf.write(String.valueOf(total).getBytes());
					rf.close();
				}
				is.close();
				raf.close();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// ֻ�����е��̶߳�������Ϻ� �ſ���ɾ����¼�ļ���
				synchronized (MutileThreadDownload.class) {
					System.out.println("�߳�" + threadId + "���������");
					runningThreadCount--;
					if (runningThreadCount < 1) {
						System.out.println("���е��̶߳���������ˡ�ɾ����ʱ��¼���ļ�");
						for (int i = 1; i <= threadCount; i++) {
							File f = new File(i + ".txt");
							System.out.println(f.delete());
						}
					}
				}

			}
		}
	}
}
