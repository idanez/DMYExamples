package my.snippets;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.LineComment;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.EmptyTypeDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.DoubleLiteralExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.ExplicitConstructorInvocationStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.sun.xml.internal.ws.org.objectweb.asm.AnnotationVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.Attribute;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.FieldVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;


public class Obfuscator {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		
		
		        // creates an input stream for the file to be parsed
		        FileInputStream in = new FileInputStream("D:\\Workspaces\\workspace\\Exemplos\\src\\MS.java");

		        CompilationUnit cu = null;
		        try {
		            // parse the file
		            cu = JavaParser.parse(in);
		        } catch (ParseException e) {
					e.printStackTrace();
				} finally {
		            in.close();
		        }
		        
		        List<TypeDeclaration> types = cu.getTypes();
		        for (TypeDeclaration typeDeclaration : types) {
		        	System.out.println("TypeDeclaration: "+typeDeclaration.getName());		        			        	
				}
		        		        
		        // visit and print the methods names
		        new MethodVisitor().visit(cu, null);
        
		    }

		    /**
		     * Simple visitor implementation for visiting MethodDeclaration nodes. 
		     */
		    private static class MethodVisitor extends VoidVisitorAdapter {

		        @Override
		        public void visit(MethodDeclaration n, Object arg) {
		            // here you can access the attributes of the method.
		            // this method will be called for all methods in this 
		            // CompilationUnit, including inner class methods
		            System.out.println("MethodDeclaration: "+n.getName());
		        }

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.expr.DoubleLiteralExpr, java.lang.Object)
				 */
				@Override
				public void visit(DoubleLiteralExpr n, Object arg) {
					//super.visit(n, arg);
					System.out.println("DoubleLiteralExpr: "+n.getValue());
				}

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.body.EmptyTypeDeclaration, java.lang.Object)
				 */
				@Override
				public void visit(EmptyTypeDeclaration n, Object arg) {
					//super.visit(n, arg);
					System.out.println("EmptyTypeDeclaration: "+n.getName());
				}

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.body.EnumDeclaration, java.lang.Object)
				 */
				@Override
				public void visit(EnumDeclaration n, Object arg) {
					System.out.println("EnumDeclaration: "+n.getName());
				}

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.stmt.ExplicitConstructorInvocationStmt, java.lang.Object)
				 */
				@Override
				public void visit(ExplicitConstructorInvocationStmt n, Object arg) {
					System.out.println("ExplicitConstructorInvocationStmt: "+n.getExpr());
				}

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.stmt.ExpressionStmt, java.lang.Object)
				 */
				@Override
				public void visit(ExpressionStmt n, Object arg) {
					System.out.println("ExpressionStmt: "+n.getExpression());
				}

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.body.FieldDeclaration, java.lang.Object)
				 */
				@Override
				public void visit(FieldDeclaration n, Object arg) {
					System.out.println("FieldDeclaration: "+n.getType());
				}

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.body.JavadocComment, java.lang.Object)
				 */
				@Override
				public void visit(JavadocComment n, Object arg) {
					System.out.println("JavadocComment: "+n.getContent());
				}

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.LineComment, java.lang.Object)
				 */
				@Override
				public void visit(LineComment n, Object arg) {
					System.out.println("LineComment: "+n.getContent());
				}

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.expr.NameExpr, java.lang.Object)
				 */
				@Override
				public void visit(NameExpr n, Object arg) {
					System.out.println("NameExpr: "+n.getName());
				}

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.body.Parameter, java.lang.Object)
				 */
				@Override
				public void visit(Parameter n, Object arg) {
					System.out.println("Parameter: "+n.getType());
				}

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.type.PrimitiveType, java.lang.Object)
				 */
				@Override
				public void visit(PrimitiveType n, Object arg) {
					System.out.println("PrimitiveType: "+n.getType());
				}

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.expr.VariableDeclarationExpr, java.lang.Object)
				 */
				@Override
				public void visit(VariableDeclarationExpr n, Object arg) {
					
					System.out.println("VariableDeclarationExpr: "+n.getType());
				}

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.body.VariableDeclarator, java.lang.Object)
				 */
				@Override
				public void visit(VariableDeclarator n, Object arg) {
					
					System.out.println("VariableDeclarator: "+n.getInit());
				}

				/* (non-Javadoc)
				 * @see japa.parser.ast.visitor.VoidVisitorAdapter#visit(japa.parser.ast.body.VariableDeclaratorId, java.lang.Object)
				 */
				@Override
				public void visit(VariableDeclaratorId n, Object arg) {
					
					System.out.println("VariableDeclaratorId: "+n.getName());
				}
		        
		        
		    }
		
//		if(args.length == 0) {
//			System.out.println("Digite o caminho completo do programa a ser ofuscado");
//			return;
//		}
//
//		System.out.println();
//		
//		String fileName = "D:\\Workspaces\\workspace\\Exemplos\\src\\CheckMS.java"; 
//
//		File f = new File(fileName);
//		StringBuffer buffer = new StringBuffer();
//
//		BufferedReader in = null;  
//		try {  
//			in = new BufferedReader(new FileReader(f));  
//			String line;  
//			while ((line = in.readLine()) != null) {  
//				buffer.append(line);
//				System.out.println(line);
//			}			
//		}  
//		catch (FileNotFoundException e) { 
//			e.printStackTrace();			
//		}  
//		catch (IOException e) {  
//			e.printStackTrace();
//		} finally {  
//			if(in != null) in.close();  
//		}  
//
//		System.out.println(buffer.toString());
		    
}
	


