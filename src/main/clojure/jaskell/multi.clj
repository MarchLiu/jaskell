(ns jaskell.multi)

(def ^{:private true} default-hierarchy
  (make-hierarchy))

(defmacro multi
  "Creates a new \"anonymous\" multimethod with the associated dispatch function.
  The docstring is optional.

  like fn, you can assign a name for this multimethod, it will pass into MultiFn
  construction function.

  Options are key-value pairs and may be one of:

  :default

  The default dispatch value, defaults to :default

  :hierarchy

  The value used for hierarchical dispatch (e.g. ::square is-a ::shape)

  Hierarchies are type-like relationships that do not depend upon type
  inheritance. By default Clojure's multimethods dispatch off of a
  global hierarchy map.  However, a hierarchy relationship can be
  created with the derive function used to augment the root ancestor
  created with make-hierarchy.

  Multimethods expect the value of the hierarchy option to be supplied as
  a reference type e.g. a var (i.e. via the Var-quote dispatch macro #'
  or the var special form)."
  {:arglists '([name? dispatch-fn & options])
   :forms    '[(multi name? dispach-fn & options)]
   :added    "1.0"}
  [& sigs]
  (let [multi-name (if (symbol? (first sigs))
               (first sigs)
               nil)
        sigs (if multi-name (next sigs) sigs)
        dispatch-fn (first sigs)
        options (next sigs)]
    (let [options (apply hash-map options)
          default (get options :default :default)
          hierarchy (get options :hierarchy #'default-hierarchy)]
      ;; (check-valid-options options :default :hierarchy)
      (let [n (if multi-name
                (name multi-name)
                "muliti*")]
         `(new clojure.lang.MultiFn ~n ~dispatch-fn ~default ~hierarchy)))))

