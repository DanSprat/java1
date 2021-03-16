1.В классе PathLoader 
   а) 2 метода process. В том который @Deprecated отбор по дате,
   полученной из файлового аттрибута. В другом отбор по имени папки, в которой лежит package
   (насколько я понял как раз для этого нужнен был такой формат папок).
   б) В папке 20210316 лежит битый класс,который не сможет загрузиться
	
2. TestClass - класс для запуска c javaagent
   VM options:"path_to_jar"=ru.progwards.java2.lessons.classloader.TestClass