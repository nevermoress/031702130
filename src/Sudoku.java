import java.io.*;
 
public class Sudoku {
		// 初始化命令行参数	
	public static String inputFilename;
	public static String outputFilename;
	public static int m;
	public static int n;
	
		//	解析命令行参数	
    public static void loadArgs(String args[]){
    	if(args.length>0&&args!=null){
    		for(int i=0;i<args.length;i++){
    			switch (args[i]) {
				case "-i":
					inputFilename  =   args[++i];
					break;
				case "-o": 
					outputFilename =   args[++i];
					break;
				case "-m": 
					m=Integer.valueOf(args[++i]);
					break;
				case "-n":
					n=Integer.valueOf(args[++i]);
				    break;
				default  :
					break;
				}
    		}
    	}
    }

	public static void main(String[] args) {	
		loadArgs(args);
		File file = new File(inputFilename);	
		int a[][]=new int[m][m];
		//从输入文件中读取数独到二维数组
		try {			
			FileReader fr = new FileReader(file);	
			BufferedReader bufr = new BufferedReader(new FileReader(file));	
		    PrintWriter out = new PrintWriter(outputFilename);
			String line = null;	
			int q=0,w=0;int e=0;
			while((line=bufr.readLine())!=null) {
				int k = 0;
				 if(!line.equals(""))
				 { 
						 e++;
						 String[] s = line.split(" ");
					 for(w=0;w<m;w++)
					 {
						 a[q][w]=Integer.parseInt(s[w]);
					 }
					 q++; 
				 }
				 if(e==m)
				 {
					 //加载数独游戏
					boolean[][] rows = new boolean[m][m];	
				    boolean[][] cols = new boolean[m][m];	
				    boolean[][] blocks = new boolean[m][m];	 
				    for (int i = 0; i < m; i++) {
						for (int j = 0; j < m; j++) {
							if(a[i][j]!=0){
								if(m==4) {
									k=i/2*2+j/2;
								}
								if(m==6) {
									k=i/2*2+j/3;
								}
								if(m==8) {
									k=i/4*4+j/2;
								}
								if(m==9) {
									k=i/3*3+j/3;
								}
								int val=a[i][j]-1;
								  rows[i][val] = true;
						          cols[j][val] = true;
						          blocks[k][val] = true;
							}
						}
				    }
				    	//数独游戏加载完毕后调用backTrace方法
				    backTrace(a, m,cols, rows, blocks);
				    
				    	//输出解完的数独到输出文件
				    	for (int i = 0; i < m; i++) {
							for (int j = 0; j < m-1; j++) {
								out.print(a[i][j]+" ");
							}
							out.print(a[i][m-1]+"\r\n");
					    }
						out.print("\r\n");
				    q=0;e=0;
				 }			 
			}			
			fr.close();
			bufr.close();
			out.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
}

	
	
	
	
		/**
		 * 声明检查唯一性和回溯的方法
		 * @param a
		 * @param g
		 * @param cols
		 * @param rows
		 * @param blocks
		 * @return
		 */
	public static boolean backTrace(int[][] a,int g,boolean[][] cols,boolean[][] rows,boolean[][] blocks) {
		int k = 0 ;
		for (int i = 0; i < g; i++) {
			for (int j = 0; j < g; j++) {
				if(a[i][j]==0){
					//不考虑宫的情况		
					if(g==3||g==5||g==7) {
						//从数字1到阶数判断是否在行列中唯一
						for (int l = 0; l < g; l++) {
							if(!cols[j][l]&&!rows[i][l]){
								   rows[i][l] = cols[j][l] = true;
								   //若满足唯一，则赋值，然后继续深度遍历
			                        a[i][j] = 1 + l;
			                        if(backTrace(a, g,cols, rows, blocks)) return true;
			                        //不满足条件则回溯
			                        rows[i][l] = cols[j][l] = false;
			                        a[i][j] = 0;  
							}
						}
					}
					//考虑宫的情况
					else {
						//按照不同的阶数划分宫
						if(g==4) {
							k=i/2*2+j/2;
						}
						if(g==6) {
							k=i/2*2+j/3;
						}
						if(g==8) {
							k=i/4*4+j/2;
						}
						if(g==9) {
							k=i/3*3+j/3;
						}
						//从数字1到阶数判断是否在行列宫中唯一
						for (int l = 0; l < g; l++) {
							if(!cols[j][l]&&!rows[i][l]&&!blocks[k][l]){
								   rows[i][l] = cols[j][l] = blocks[k][l] = true;
								   //若满足唯一，则赋值，然后继续深度遍历
			                        a[i][j] = 1 + l;
			                        if(backTrace(a, g,cols, rows, blocks)) return true;
			                      //不满足条件则回溯
			                        rows[i][l] = cols[j][l] = blocks[k][l] = false;
			                        a[i][j] = 0;  
							}
						}
					}

					return false;
				}
			}
		}
		return true;
	}
}
