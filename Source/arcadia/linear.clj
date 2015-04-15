(ns arcadia.linear
  (:use arcadia.core)
  (:require [arcadia.internal.meval :as mvl]
            [clojure.zip :as zip]
            [arcadia.internal.zip-utils :as zu])
  (:import [UnityEngine Vector2 Vector3 Vector4]))

;; Much of the implementation logic here is due to current uncertainty
;; about the status of the polymorphic inline cache. We have a pretty
;; good one thanks to the DLR, but only for certain export
;; targets. Future versions of this library may switch on their export
;; target, as the presence or absence of a strong PIC would have
;; significant performance implications for this sort of thing.

;; ============================================================
;; div

(definline v2div [a b]
  `(Vector2/op_Division ~a ~b))

(definline v3div [a b]
  `(Vector3/op_Division ~a ~b))

(definline v4div [a b]
  `(Vector4/op_Division ~a ~b))

(definline vdiv [a b]
  `(condcast-> ~a a#
     Vector3 (v3div a# ~b)
     Vector2 (v2div a# ~b)
     Vector4 (v4div a# ~b)))

;; ============================================================
;; +

(definline v2+ [a b]
  `(Vector2/op_Addition ~a ~b))

(definline v3+ [a b]
  `(Vector3/op_Addition ~a ~b))

(definline v4+ [a b]
  `(Vector4/op_Addition ~a ~b))

(definline v+ [a b]
  `(condcast-> ~a a#
     Vector3 (v3+ a# ~b)
     Vector2 (v2+ a# ~b)
     Vector4 (v4+ a# ~b)))

(comment
  (definline v2blo [a b]
    `(Vector2/op_bla ~a ~b))

  (definline v3blo [a b]
    `(Vector3/op_bla ~a ~b))

  (definline v4blo [a b]
    `(Vector4/op_bla ~a ~b)))

;; ============================================================
;; -

(definline v2- [a b]
  `(Vector2/op_Subtraction ~a ~b))

(definline v3- [a b]
  `(Vector3/op_Subtraction ~a ~b))

(definline v4- [a b]
  `(Vector4/op_Subtraction ~a ~b))

(definline v- [a b]
  `(condcast-> ~a a#
     Vector3 (v3- a# ~b)
     Vector2 (v2- a# ~b)
     Vector4 (v4- a# ~b)))

;; ============================================================
;; *

(definline v2* [a b]
  `(Vector2/op_Multiply ~a ~b))

(definline v3* [a b]
  `(Vector3/op_Multiply ~a ~b))

(definline v4* [a b]
  `(Vector4/op_Multiply ~a ~b))

;; this one requires some more thought, of course
(definline v* [a b]
  `(condcast-> ~a a#
     Vector3 (v3* a# ~b)
     Vector2 (v2* a# ~b)
     Vector4 (v4* a# ~b)))

;; ============================================================
;; scale

(definline v2scale [a b]
  `(Vector2/op_Scale ~a ~b))

(definline v3scale [a b]
  `(Vector3/op_Scale ~a ~b))

(definline v4scale [a b]
  `(Vector4/op_Scale ~a ~b))

(definline vscale [a b]
  `(condcast-> ~a a#
     Vector3 (v3scale a# ~b)
     Vector2 (v2scale a# ~b)
     Vector4 (v4scale a# ~b)))
