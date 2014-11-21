#!/usr/bin/env ruby

USAGE = "Usage: #{File.basename __FILE__} IteratorName (without Iterator, e.g. Merging)"

name = ARGV[0] or abort(USAGE)
abort("Iterator name must start with a capital letter") unless name =~ /^[A-Z]/

class_filename = "src/main/scala/#{name}Iterator.scala"
spec_filename = "src/test/scala/#{name}IteratorSpec.scala"

abort("#{class_filename} already exists") if File.exists? class_filename
abort("#{spec_filename} already exists") if File.exists? spec_filename

File.open(class_filename, "w") do |io|
    io.write <<-EOT
package net.detunized.iteratorz

class #{name}Iterator[A](source: Iterator[A]) extends Iterator[A] {
  def hasNext: Boolean = source.hasNext

  def next(): A = source.next()
}
EOT
end

File.open(spec_filename, "w") do |io|
    io.write <<-EOT
package net.detunized.iteratorz

class #{name}IteratorSpec extends IteratorSpec[Int] {
  "..." in {
    ok
  }

  protected[this] def mkEmpty = mk()

  private[this] def mk(s: T*) =
    new #{name}Iterator(s.iterator)
}
EOT
end
